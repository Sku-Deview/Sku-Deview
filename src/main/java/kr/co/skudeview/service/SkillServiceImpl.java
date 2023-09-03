package kr.co.skudeview.service;

import jakarta.transaction.Transactional;
import kr.co.skudeview.domain.Skill;
import kr.co.skudeview.infra.exception.DuplicatedException;
import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.repository.SkillRepository;
import kr.co.skudeview.service.dto.request.SkillRequestDto;
import kr.co.skudeview.service.dto.response.SkillResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    @Transactional
    public void createSkill(SkillRequestDto.CREATE create) {
        isSkillName(create.getName());

        skillRepository.save(toEntity(create));
    }

    @Override
    public List<SkillResponseDto.READ> getAllSkills() {
        List<Skill> skills = skillRepository.findAllByDeleteAtFalse();

        return skills.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateSkill(SkillRequestDto.UPDATE update) {
        final Optional<Skill> skill = skillRepository.findSkillByIdAndDeleteAtFalse(update.getSkillId());

        isSkill(skill);

        isSkillName(update.getName());

        skill.get().updateSkill(update);

        skillRepository.save(skill.get());
    }

    @Override
    @Transactional
    public void deleteSkill(Long skillId) {
        final Optional<Skill> skill = skillRepository.findSkillByIdAndDeleteAtFalse(skillId);

        isSkill(skill);

        skill.get().changeDeleteAt();

        skillRepository.save(skill.get());
    }

    private void isSkill(Optional<Skill> skill) {
        if (skill.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_SKILL_NOT_FOUND);
        }
    }

    private void isSkillName(String name) {
        if (skillRepository.existsSkillByNameAndDeleteAtFalse(name)) {
            throw new DuplicatedException(ResponseStatus.FAIL_SKILL_NAME_DUPLICATED);
        }
    }

    private Skill toEntity(SkillRequestDto.CREATE create) {
        return Skill.builder()
                .name(create.getName())
                .build();
    }

    private SkillResponseDto.READ toReadDto(Skill skill) {
        return SkillResponseDto.READ.builder()
                .skillId(skill.getId())
                .name(skill.getName())
                .build();
    }
}

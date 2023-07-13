package kr.co.skudeview.service;

import jakarta.transaction.Transactional;
import kr.co.skudeview.domain.Skill;
import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.repository.SkillRepository;
import kr.co.skudeview.service.dto.request.SkillRequestDto;
import kr.co.skudeview.service.dto.response.SkillResponseDto;
import kr.co.skudeview.service.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillMapper skillMapper;

    private final SkillRepository skillRepository;

    @Override
    @Transactional
    public void createSkill(SkillRequestDto.CREATE create) {
        isSkillName(create.getName());

        final Skill skill = skillMapper.toEntity(create);

        skillRepository.save(skill);
    }

    @Override
    public List<SkillResponseDto.READ> getAllSkills() {
        final List<Skill> skills = skillRepository.findAllByDeleteAtFalse();

        return skills.stream()
                .map(skillMapper::toReadDto)
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

    // 해당 skill이 존재하는지
    private void isSkill(Optional<Skill> skill) {
        if (skill.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_SKILL_NOT_FOUND);
        }
    }

    // skill name이 중복인지
    private void isSkillName(String name) {
        if (skillRepository.existsSkillByNameAndDeleteAtFalse(name)) {
            throw new NotFoundException(ResponseStatus.FAIL_SKILL_NAME_DUPLICATED);
        }
    }

//    private void isSkillCount()

}

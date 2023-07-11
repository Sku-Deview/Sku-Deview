package kr.co.skudeview.service;

import jakarta.transaction.Transactional;
import kr.co.skudeview.domain.Skill;
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
    public void createSkill(SkillRequestDto.CREATE create) throws Exception{
        isSkillName(create.getName());

        final Skill skill = skillMapper.toEntity(create);

        skillRepository.save(skill);
    }

    @Override
    public SkillResponseDto.READ getSkillBySkillId(Long skillId) throws Exception {
        final Optional<Skill> skill = skillRepository.findSkillById(skillId);

        isSkill(skill);

        return skillMapper.toReadDto(skill.get());
    }

    @Override
    public List<SkillResponseDto.READ> getAllSkills() {
        final List<Skill> skills = skillRepository.findAll();

        return skills.stream()
                .map(skillMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateSkill(SkillRequestDto.UPDATE update) throws Exception {
        final Optional<Skill> skill = skillRepository.findById(update.getSkillId());

        isSkill(skill);

        isSkillName(update.getName());

        skill.get().updateSkill(update);

        skillRepository.save(skill.get());
    }

    @Override
    @Transactional
    public void deleteSkill(Long skillId) throws Exception {
        final Optional<Skill> skill = skillRepository.findById(skillId);

        isSkill(skill);

        skill.get().changeDeleteAt();

        skillRepository.save(skill.get());
    }

    // skill name이 중복인지
    private void isSkillName(String name) throws Exception {
        if (skillRepository.existsSkillByName(name)) {
            throw new Exception("This Skill is Already Exist");
        }
    }

    // 해당 skill이 존재하는지
    private void isSkill(Optional<Skill> skill) throws Exception {
        if (skill.isEmpty()) {
            throw new Exception("This Skill is Not Exist");
        }
    }
}

package kr.co.skudeview.service;

import kr.co.skudeview.domain.Skill;
import kr.co.skudeview.service.dto.request.SkillRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;
import kr.co.skudeview.service.dto.response.SkillResponseDto;

import java.util.List;

public interface SkillService {

    void createSkill(SkillRequestDto.CREATE create);

    List<SkillResponseDto.READ> getAllSkills();

    void updateSkill(SkillRequestDto.UPDATE update);

    void deleteSkill(Long skillId);

    default Skill toEntity(SkillRequestDto.CREATE create) {
        return Skill.builder()
                .name(create.getName())
                .build();
    }

    default SkillResponseDto.READ toReadDto(Skill skill) {
        return SkillResponseDto.READ.builder()
                .skillId(skill.getId())
                .name(skill.getName())
                .build();
    }

}

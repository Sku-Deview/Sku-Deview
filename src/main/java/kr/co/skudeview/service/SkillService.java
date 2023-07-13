package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.SkillRequestDto;
import kr.co.skudeview.service.dto.response.SkillResponseDto;

import java.util.List;

public interface SkillService {

    void createSkill(SkillRequestDto.CREATE create);

    List<SkillResponseDto.READ> getAllSkills();

    void updateSkill(SkillRequestDto.UPDATE update);

    void deleteSkill(Long skillId);

}

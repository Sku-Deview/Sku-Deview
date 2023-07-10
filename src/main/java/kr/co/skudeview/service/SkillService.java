package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.SkillRequestDto;
import kr.co.skudeview.service.dto.response.SkillResponseDto;

import java.util.List;

public interface SkillService {

    void createSkill(SkillRequestDto.CREATE create) throws Exception;

    SkillResponseDto.READ getSkillBySkillId(Long skillId) throws Exception;

    List<SkillResponseDto.READ> getAllSkills();

    void updateSkill(SkillRequestDto.UPDATE update) throws Exception;

    void deleteSkill(Long skillId) throws Exception;

}

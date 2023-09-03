package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.SkillRequestDto;
import kr.co.skudeview.service.dto.response.SkillResponseDto;

import java.util.List;

public interface SkillService {

    /**
     * 새로운 기술을 생성.
     *
     * @param create 생성할 기술 정보를 담고 있는 DTO
     */
    void createSkill(SkillRequestDto.CREATE create);

    /**
     * 모든 기술 목록을 조회.
     *
     * @return 모든 기술 목록을 담은 DTO 리스트 (List<SkillResponseDto.READ>)
     */
    List<SkillResponseDto.READ> getAllSkills();

    /**
     * 기술 정보를 수정.
     *
     * @param update 업데이트할 기술 정보를 담고 있는 DTO
     */
    void updateSkill(SkillRequestDto.UPDATE update);

    /**
     * 기술을 삭제.
     *
     * @param skillId 삭제할 기술의 ID
     */
    void deleteSkill(Long skillId);
}

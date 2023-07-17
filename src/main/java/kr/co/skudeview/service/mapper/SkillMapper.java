package kr.co.skudeview.service.mapper;

import kr.co.skudeview.domain.Skill;
import kr.co.skudeview.service.dto.request.SkillRequestDto;
import kr.co.skudeview.service.dto.response.SkillResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SkillMapper {

//    Skill toEntity(SkillRequestDto.CREATE create);
//
//    @Mapping(target = "skillId", source = "id")
//    SkillResponseDto.READ toReadDto(Skill skill);
}

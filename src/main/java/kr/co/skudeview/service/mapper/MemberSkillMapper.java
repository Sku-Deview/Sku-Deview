package kr.co.skudeview.service.mapper;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.MemberSkill;
import kr.co.skudeview.domain.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberSkillMapper {

    MemberSkill toEntity(Skill skill, Member member);

}

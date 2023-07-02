package kr.co.skudeview.service.mapper;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member toEntity(MemberRequestDto.CREATE create);

    @Mapping(target = "memberId", source = "id")
    MemberResponseDto.READ toReadDto(Member member);
}

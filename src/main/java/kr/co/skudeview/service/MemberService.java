package kr.co.skudeview.service;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.enums.Gender;
import kr.co.skudeview.domain.enums.Role;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public interface MemberService {

    void createMember(MemberRequestDto.CREATE create);

    MemberResponseDto.READ getMemberDetail(Long memberId);

    List<MemberResponseDto.READ> getAllMembers();

    List<MemberResponseDto.READ> getSearchMembers(MemberRequestDto.CONDITION condition);

    void updateMember(MemberRequestDto.UPDATE update);

    void deleteMember(Long id);

    Set<String> getSkillsNameByMember(Member member);

    MemberResponseDto.READ loginMember(MemberRequestDto.LOGIN login);

    default Member toEntity(MemberRequestDto.CREATE create) {
        return Member.builder()
                .email(create.getEmail())
                .password(create.getPassword())
                .name(create.getName())
                .nickname(create.getNickname())
                .telephone(create.getTelephone())
                .address(create.getAddress())
                .birthDate(create.getBirthDate())
                .gender(Gender.valueOf(create.getGender()))
                .role(Role.valueOf(create.getRole()))
                .memberSkills(Collections.emptyList())
                .build();
    }

    default MemberResponseDto.READ toReadDto(Member member) {
        return MemberResponseDto.READ.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .telephone(member.getTelephone())
                .address(member.getAddress())
                .birthDate(member.getBirthDate())
                .gender(String.valueOf(member.getGender()))
                .role(String.valueOf(member.getGender()))
                .skillName(getSkillsNameByMember(member))
                .build();
    }

}


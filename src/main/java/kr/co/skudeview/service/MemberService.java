package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;

import java.util.List;

public interface MemberService {

    void createMember(MemberRequestDto.CREATE create);

    MemberResponseDto.READ getMemberDetail(Long memberId);

    List<MemberResponseDto.READ> getAllMembers();

    void updateMember(MemberRequestDto.UPDATE update);

    void deleteMember(Long id);

}


package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;

import java.util.List;

public interface MemberService {

    /**
     * TODO
     *
     * Exception 추가해준것 모두 삭제 예정 -> Custom 한 Exception과 ResponseStatus추가 후 변경 예정
      */

    void createMember(MemberRequestDto.CREATE create) throws Exception;

    // 추후 조회의 경우 동적쿼리를 이용하여 One, List 모두 해결이 가능
    MemberResponseDto.READ getMemberByEmail(String email) throws Exception;

    List<MemberResponseDto.READ> getAllMembers() throws Exception;

    void updateMember(MemberRequestDto.UPDATE update) throws Exception;

    void deleteMember(Long id) throws Exception;

}


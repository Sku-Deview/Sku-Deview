package kr.co.skudeview.domain.member.service;

import kr.co.skudeview.domain.member.dto.MemberRequestDto;
import kr.co.skudeview.domain.member.dto.MemberResponseDto;
import kr.co.skudeview.global.auth.TokenDto;
import kr.co.skudeview.domain.member.entity.Member;

import java.util.List;
import java.util.Set;

public interface MemberService {

    /**
     * 새로운 회원을 생성.
     *
     * @param create 새 회원 정보를 담고 있는 DTO
     */
    void createMember(MemberRequestDto.CREATE create);

    /**
     * 특정 회원의 상세 정보를 조회.
     *
     * @param memberId 조회할 회원의 ID
     * @return 조회된 회원의 상세 정보를 담은 DTO (MemberResponseDto.READ)
     */
    MemberResponseDto.READ getMemberDetail(Long memberId);

    MemberResponseDto.READ getMemberDetailByMemberNickname(String memberNickname);

    /**
     * 모든 회원의 목록을 조회.
     *
     * @return 모든 회원의 목록을 담은 DTO 리스트 (List<MemberResponseDto.READ>)
     */
    List<MemberResponseDto.READ> getAllMembers();

    /**
     * 조건에 맞는 회원들을 검색.
     *
     * @param condition 검색 조건을 담고 있는 DTO
     * @return 조건에 맞는 회원들의 목록을 담은 DTO 리스트 (List<MemberResponseDto.READ>)
     */
    List<MemberResponseDto.READ> getSearchMembers(MemberRequestDto.CONDITION condition);

    /**
     * 회원 정보를 수정.
     *
     * @param email  수정할 회원의 이메일
     * @param update 업데이트할 회원 정보를 담고 있는 DTO
     */
    void updateMember(String email, MemberRequestDto.UPDATE update);

    /**
     * 회원을 삭제.
     *
     * @param id 삭제할 회원의 ID
     */
    void deleteMember(Long id);

    /**
     * 회원의 보유 기술 목록을 조회.
     *
     * @param member 회원 객체
     * @return 회원의 보유 기술 목록 (Set<String>)
     */
    Set<String> getSkillsNameByMember(Member member);

    /**
     * 회원 로그인을 처리하고, 로그인된 회원 정보를 반환.
     *
     * @param login 로그인 정보를 담고 있는 DTO
     * @return 로그인된 회원 정보를 담은 DTO (MemberResponseDto.READ)
     */
    MemberResponseDto.READ loginMember(MemberRequestDto.LOGIN login);

    /**
     * 액세스 토큰을 새로고침.
     *
     * @param tokenDto 토큰 정보를 담고 있는 DTO
     * @return 새로고침된 액세스 토큰을 담은 DTO (TokenDto)
     */
    TokenDto refreshAccessToken(TokenDto tokenDto);

    String checkEmailValid(String email);

    String checkNicknameValid(String nickname);

    String checkTelephoneValid(String telephone);
}

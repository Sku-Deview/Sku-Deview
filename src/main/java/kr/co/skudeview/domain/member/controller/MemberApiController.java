package kr.co.skudeview.domain.member.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.domain.member.dto.MemberRequestDto;
import kr.co.skudeview.domain.member.dto.MemberResponseDto;
import kr.co.skudeview.domain.member.service.MemberService;
import kr.co.skudeview.global.auth.TokenDto;
import kr.co.skudeview.global.model.ResponseFormat;
import kr.co.skudeview.global.model.ResponseStatus;
import kr.co.skudeview.global.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    /**
     * Create Member API
     *
     * @param create
     * @return ResponseStatus.SUCCESS_CREATE + Void
     */
    @PostMapping("/member")
    public ResponseFormat<Void> createMember(@RequestBody @Valid MemberRequestDto.CREATE create) {
        memberService.createMember(create);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    /**
     * @param login
     * @return ResponseStatus.SUCCESS_OK + MemberResponseDto.READ
     */
    @PostMapping("/login")
    public ResponseFormat<MemberResponseDto.READ> loginMember(@RequestBody @Valid MemberRequestDto.LOGIN login) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,memberService.loginMember(login));
    }

    /**
     * Update Member API
     *
     * @param update
     * @return ResponseStatus.SUCCESS_NO_CONTENT + Void
     */
    @PutMapping("/member/{memberEmail}")
    public ResponseFormat<Void> updateMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable String memberEmail,
                                             @RequestBody @Valid MemberRequestDto.UPDATE update) {
//        memberService.updateMember(userDetails.getUsername(), update);
        memberService.updateMember(memberEmail, update);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    /**
     * Delete Member API
     *
     * @param memberId
     * @return ResponseStatus.SUCCESS_NO_CONTENT + Void
     */
    @DeleteMapping("/member/{memberId}")
    public ResponseFormat<Void> deleteMember(@PathVariable(name = "memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    /**
     * Read Member API - memberId 값으로 단일 조회
     *
     * @param memberId
     * @return ResponseStatus.SUCCESS_OK + MemberResponseDto.READ
     */
    @GetMapping("/member/{memberId}")
    public ResponseFormat<MemberResponseDto.READ> getMemberDetail(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMemberDetail(memberId));
    }

    @PostMapping("/member/{memberNickname}")
    public ResponseFormat<MemberResponseDto.READ> getMemberDetailByMemberNickname(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMemberDetailByMemberNickname(memberNickname));
    }

    /**
     * Read Member API - 모든 member 다중 조회
     *
     * @return ResponseStatus.SUCCESS_OK + List<MemberResponseDto.READ>
     */
    @GetMapping("/member")
    public ResponseFormat<List<MemberResponseDto.READ>> getAllMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllMembers());
    }

    /**
     * Search Member API - 검색 조건에 맞는 member 다중 조회
     *
     * @param condition
     * @return ResponseStatus.SUCCESS_OK + List<MemberResponseDto.READ>
     */
    @GetMapping("/member/search")
    public ResponseFormat<List<MemberResponseDto.READ>> getSearchMembers(@RequestBody @Valid MemberRequestDto.CONDITION condition) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getSearchMembers(condition));
    }

    /**
     * Refresh
     *
     * @Param tokenDto
     * @Return
     */
    @GetMapping("/refresh")
    public ResponseFormat<TokenDto> refresh(@RequestBody @Valid TokenDto tokenDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.refreshAccessToken(tokenDto));
    }






    /** 회원가입 시, 중복 확인 버튼 체크 위한 API **/

    @PostMapping("/member/checkEmail/{email}")
    public ResponseFormat<String> checkEmailValid(@PathVariable String email) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.checkEmailValid(email));
    }

    @PostMapping("/member/checkNickname/{nickname}")
    public ResponseFormat<String> checkNicknameValid(@PathVariable String nickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.checkNicknameValid(nickname));
    }

    @PostMapping("/member/checkTelephone/{telephone}")
    public ResponseFormat<String> checkTelephoneValid(@PathVariable String telephone) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.checkTelephoneValid(telephone));
    }

}

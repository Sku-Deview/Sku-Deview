package kr.co.skudeview.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.MemberService;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApiController {

    private final MemberService memberService;

    /**
     * Create Member API
     * @param create
     * @return ResponseStatus.SUCCESS_CREATE + Void
     */
    @PostMapping("/member")
    public ResponseFormat<Void> createMember(@RequestBody @Valid MemberRequestDto.CREATE create) {
        memberService.createMember(create);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    /**
     * Update Member API
     * @param update
     * @return ResponseStatus.SUCCESS_NO_CONTENT + Void
     */
    @PutMapping("/member")
    public ResponseFormat<Void> updateMember(@RequestBody @Valid MemberRequestDto.UPDATE update) {
        memberService.updateMember(update);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    /**
     * Delete Member API
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
     * @param memberId
     * @return ResponseStatus.SUCCESS_OK + MemberResponseDto.READ
     */
    @GetMapping("/member/{memberId}")
    public ResponseFormat<MemberResponseDto.READ> getMemberDetail(@PathVariable(name= "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMemberDetail(memberId));
    }

    /**
     * Read Member API - 모든 member 다중 조회
     * @return ResponseStatus.SUCCESS_OK + List<MemberResponseDto.READ>
     */
    @GetMapping("/member")
    public ResponseFormat<List<MemberResponseDto.READ>> getAllMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllMembers());
    }

}
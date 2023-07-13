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


    @PostMapping("/member")
    public ResponseFormat<Void> createMember(@RequestBody @Valid MemberRequestDto.CREATE create) {
        memberService.createMember(create);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    @PutMapping("/member")
    public ResponseFormat<Void> updateMember(@RequestBody @Valid MemberRequestDto.UPDATE update) {
        memberService.updateMember(update);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseFormat<Void> deleteMember(@PathVariable(name = "memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    // 회원 단일 조회
    @GetMapping("/member/{memberId}")
    public ResponseFormat<MemberResponseDto.READ> getMemberDetail(@PathVariable(name= "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getMemberDetail(memberId));
    }

    // 모든 회원 조회
    @GetMapping("/member")
    public ResponseFormat<List<MemberResponseDto.READ>> getAllMembers() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, memberService.getAllMembers());
    }

}

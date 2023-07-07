package kr.co.skudeview.controller;

import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<Void> createMember(@RequestBody @Valid MemberRequestDto.CREATE create) throws Exception {
        memberService.createMember(create);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateMember(@RequestBody @Valid MemberRequestDto.UPDATE update) throws Exception {
        memberService.updateMember(update);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{member_id}")
    public ResponseEntity<Void> deleteMember(@PathVariable(name = "member_id") Long memberId) throws Exception {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 이메일로 회원 단일 조회
    @GetMapping("/get")
    public ResponseEntity<MemberResponseDto.READ> getMember(@RequestParam String email) throws Exception {
        return new ResponseEntity<>(memberService.getMemberByEmail(email), HttpStatus.OK);
    }

    // 모든 회원 조회
    @GetMapping("/all")
    public ResponseEntity<List<MemberResponseDto.READ>> getAllMembers() throws Exception {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }


}

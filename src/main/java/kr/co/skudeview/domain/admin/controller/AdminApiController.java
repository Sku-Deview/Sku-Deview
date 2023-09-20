package kr.co.skudeview.domain.admin.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.domain.admin.service.AdminService;
import kr.co.skudeview.domain.member.dto.MemberRequestDto;
import kr.co.skudeview.domain.member.dto.MemberResponseDto;
import kr.co.skudeview.domain.post.dto.PostResponseDto;
import kr.co.skudeview.domain.reply.dto.ReplyResponseDto;
import kr.co.skudeview.domain.report.entity.dto.ReportResponseDto;
import kr.co.skudeview.domain.skill.dto.SkillRequestDto;
import kr.co.skudeview.domain.skill.dto.SkillResponseDto;
import kr.co.skudeview.global.model.ResponseFormat;
import kr.co.skudeview.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    @GetMapping("/member")
    public ResponseFormat<List<MemberResponseDto.adminREAD>> getAllMember() {
        return ResponseFormat.successWithData(kr.co.skudeview.global.model.ResponseStatus.SUCCESS_OK, adminService.getAllMember());
    }

    @PutMapping("/member/{memberId}")
    public ResponseFormat<Long> updateMember(@PathVariable(name = "memberId") Long memberId, @RequestBody @Valid MemberRequestDto.UPDATE update) {

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.updateMember(memberId, update));
    }

    @GetMapping("/member-detail/{memberId}")
    public ResponseFormat<MemberResponseDto.adminREAD> getMemberDetail(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getMemberDetail(memberId));
    }

    @GetMapping("/member-post/{memberId}")
    public ResponseFormat<List<PostResponseDto.READ>> getAllMemberPost(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllMemberPost(memberId));
    }

    @GetMapping("/post-detail/{postId}")
    public ResponseFormat<PostResponseDto.READ> getPostDetail(@PathVariable(name = "postId") Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getPostDetail(postId));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseFormat<Long> deletePost(@PathVariable(name = "postId") Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.deletePost(postId));
    }

    @GetMapping("/reply/{memberId}")
    public ResponseFormat<List<ReplyResponseDto.adminREAD>> getAllRepliesByMember(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllRepliesByMember(memberId));
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseFormat<Long> deleteReply(@PathVariable(name = "replyId") Long replyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.deleteReply(replyId));
    }

    @GetMapping("/skill")
    public ResponseFormat<List<SkillResponseDto.READ>> getAllSkills() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllSkills());
    }

    @PutMapping("/skill")
    public ResponseFormat<Void> updateSkill(@RequestBody SkillRequestDto.UPDATE update) {
        adminService.updateSkill(update);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    @GetMapping("/report")
    public ResponseFormat<List<ReportResponseDto.READ>> getAllReports() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getAllReports());
    }

    @GetMapping("/report-detail/{reportId}")
    public ResponseFormat<ReportResponseDto.READ> getReportDetail(@PathVariable(name = "reportId") Long reportId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, adminService.getReportDetail(reportId));
    }
}

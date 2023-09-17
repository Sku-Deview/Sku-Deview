package kr.co.skudeview.domain.admin.service;

import kr.co.skudeview.domain.member.dto.MemberRequestDto;
import kr.co.skudeview.domain.member.dto.MemberResponseDto;
import kr.co.skudeview.domain.post.dto.PostResponseDto;
import kr.co.skudeview.domain.reply.dto.ReplyResponseDto;
import kr.co.skudeview.domain.report.entity.dto.ReportResponseDto;
import kr.co.skudeview.domain.skill.dto.SkillRequestDto;
import kr.co.skudeview.domain.skill.dto.SkillResponseDto;

import java.util.List;

public interface AdminService {
    //Member
    List<MemberResponseDto.READ> getAllMember();

    Long updateMember(Long memberId, MemberRequestDto.UPDATE update);


    MemberResponseDto.READ getMemberDetail(Long memberId);

    //Post
    List<PostResponseDto.READ> getAllMemberPost(Long memberId);

    PostResponseDto.READ getPostDetail(final Long postId);

    Long deletePost(final Long postId);

    //Reply

    List<ReplyResponseDto.READ> getAllRepliesByMember(Long memberId);

    Long deleteReply(Long replyId);


    //Skill

    List<SkillResponseDto.READ> getAllSkills();

    void updateSkill(SkillRequestDto.UPDATE update);


    //Report
    List<ReportResponseDto.READ> getAllReports();

    ReportResponseDto.READ getReportDetail(Long reportId);

}

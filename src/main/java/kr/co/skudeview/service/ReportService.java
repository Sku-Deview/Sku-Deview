package kr.co.skudeview.service;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.Post;
import kr.co.skudeview.domain.Report;
import kr.co.skudeview.domain.enums.ReportCategory;
import kr.co.skudeview.service.dto.request.ReportRequestDto;
import kr.co.skudeview.service.dto.response.ReportResponseDto;

import java.util.List;

public interface ReportService {

    void createReport(Long postId, ReportRequestDto.CREATE create);

    List<ReportResponseDto.READ> getAllReportsByPost(Long postId);

    ReportResponseDto.READ getReportDetail(Long reportId);

    List<ReportResponseDto.READ> getAllReports();

    List<ReportResponseDto.READ> getSearchReports(ReportRequestDto.CONDITION condition);

    default Report toEntity(ReportRequestDto.CREATE create, Member reporter, Post reportPost) {
        return Report.builder()
                .member(reporter)
                .post(reportPost)
                .reportCategory(ReportCategory.valueOf(create.getReportCategory()))
                .title(create.getTitle())
                .description(create.getDescription())
                .build();
    }

    default ReportResponseDto.READ toReadDto(Report report) {
        return ReportResponseDto.READ.builder()
                .reportId(report.getId())
                .postId(report.getPost().getId())
                .memberEmail(report.getMember().getEmail())
                .memberName(report.getMember().getName())
                .reportCategory(String.valueOf(report.getReportCategory()))
                .postTitle(report.getPost().getTitle())
                .title(report.getTitle())
                .description(report.getDescription())
                .build();

    }
}

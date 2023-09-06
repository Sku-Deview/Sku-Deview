package kr.co.skudeview.domain.report.service;

import kr.co.skudeview.domain.report.entity.dto.ReportRequestDto;
import kr.co.skudeview.domain.report.entity.dto.ReportResponseDto;

import java.util.List;

public interface ReportService {

    /**
     * 신고를 생성합.
     *
     * @param email   신고를 생성할 회원의 이메일
     * @param postId  신고 대상 포스트의 ID
     * @param create  생성할 신고 정보를 담고 있는 DTO
     */
    void createReport(String email, Long postId, ReportRequestDto.CREATE create);

    /**
     * 특정 포스트에 대한 모든 신고 목록을 조회.
     *
     * @param postId 조회할 포스트의 ID
     * @return 모든 신고 목록을 담은 DTO 리스트 (List<ReportResponseDto.READ>)
     */
    List<ReportResponseDto.READ> getAllReportsByPost(Long postId);

    /**
     * 특정 신고의 상세 정보를 조회.
     *
     * @param reportId 조회할 신고의 ID
     * @return 조회된 신고의 상세 정보를 담은 DTO (ReportResponseDto.READ)
     */
    ReportResponseDto.READ getReportDetail(Long reportId);

    /**
     * 모든 신고 목록을 조회.
     *
     * @return 모든 신고 목록을 담은 DTO 리스트 (List<ReportResponseDto.READ>)
     */
    List<ReportResponseDto.READ> getAllReports();

    /**
     * 조건에 맞는 신고 목록을 검색.
     *
     * @param condition 검색 조건을 담고 있는 DTO
     * @return 조건에 맞는 신고 목록을 담은 DTO 리스트 (List<ReportResponseDto.READ>)
     */
    List<ReportResponseDto.READ> getSearchReports(ReportRequestDto.CONDITION condition);
}

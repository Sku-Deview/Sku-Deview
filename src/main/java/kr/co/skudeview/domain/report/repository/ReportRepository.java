package kr.co.skudeview.domain.report.repository;

import kr.co.skudeview.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    // reportId 값으로 report 단일 조회 + 삭제되지 않은 report
    Optional<Report> findReportByIdAndDeleteAtFalse(Long id);

    // portId 값으로 report 다중 조회 + 삭제되지 않은 report
    List<Report> findReportsByPostIdAndDeleteAtFalse(Long postId);

    // report 다중 조회 + 삭제되지 않은 report
    List<Report> findReportsByDeleteAtFalse();

    // postId, memberId를 이용하여 이미 신고한 것인지 체크
    boolean existsReportByPost_IdAndMember_IdAndDeleteAtFalse(Long postId, Long memberId);

    Optional<Report> findReportById(Long reportId);

}

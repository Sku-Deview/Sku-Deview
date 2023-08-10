package kr.co.skudeview.service;

import jakarta.transaction.Transactional;
import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.Post;
import kr.co.skudeview.domain.Report;
import kr.co.skudeview.domain.enums.ReportCategory;
import kr.co.skudeview.infra.exception.DuplicatedException;
import kr.co.skudeview.infra.exception.NotFoundException;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.repository.MemberRepository;
import kr.co.skudeview.repository.PostRepository;
import kr.co.skudeview.repository.ReportRepository;
import kr.co.skudeview.repository.search.ReportSearchRepository;
import kr.co.skudeview.service.dto.request.ReportRequestDto;
import kr.co.skudeview.service.dto.response.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    private final ReportSearchRepository reportSearchRepository;

    @Override
    @Transactional
    public void createReport(String email, Long postId, ReportRequestDto.CREATE create) {
        isReportCategory(create.getReportCategory());

        Optional<Member> findMember = memberRepository.findMemberByEmailAndDeleteAtFalse(email);
        isMember(findMember);

        Optional<Post> findPost = postRepository.findPostByIdAndDeleteAtFalse(postId);
        isPost(findPost);

        isReportDuplicated(findPost.get().getId(), findMember.get().getId());

        reportRepository.save(toEntity(create, findMember.get(), findPost.get()));
    }

    @Override
    public List<ReportResponseDto.READ> getAllReportsByPost(Long postId) {
        List<Report> reports = reportRepository.findReportsByPostIdAndDeleteAtFalse(postId);
        return reports.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponseDto.READ getReportDetail(Long reportId) {
        Optional<Report> report = reportRepository.findReportByIdAndDeleteAtFalse(reportId);

        isReport(report);

        return toReadDto(report.get());
    }

    @Override
    public List<ReportResponseDto.READ> getAllReports() {
        List<Report> reports = reportRepository.findReportsByDeleteAtFalse();

        return reports.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportResponseDto.READ> getSearchReports(ReportRequestDto.CONDITION condition) {
        List<Report> reports = reportSearchRepository.find(condition);

        return reports.stream()
                .map(this::toReadDto)
                .collect(Collectors.toList());
    }


    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_MEMBER_NOT_FOUND);
        }
    }

    private void isPost(Optional<Post> post) {
        if (post.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_POST_NOT_FOUND);
        }
    }

    private void isReport(Optional<Report> report) {
        if (report.isEmpty()) {
            throw new NotFoundException(ResponseStatus.FAIL_REPORT_NOT_FOUND);
        }
    }

    private void isReportDuplicated(Long postId, Long memberId) {
        if (reportRepository.existsReportByPost_IdAndMember_IdAndDeleteAtFalse(postId, memberId)) {
            throw new DuplicatedException(ResponseStatus.FAIL_REPORT_DUPLICATED);
        }
    }

    private void isReportCategory(String reportCategory) {
        ReportCategory.of(reportCategory);
    }

}

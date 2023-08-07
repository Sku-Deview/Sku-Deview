package kr.co.skudeview.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.ReportService;
import kr.co.skudeview.service.dto.request.ReportRequestDto;
import kr.co.skudeview.service.dto.response.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReportApiController {

    /*
    신고의 경우 수정이나 철회가 불가능하게 하는 이유
     : 일반적으로 이러한 정책은 신고 프로세스의 신뢰성과 신뢰를 유지하기 위함

    1. 신고의 불변성 유지: 수정이나 철회를 허용한다면, 이는 신고한 내용을 언제든지 변경할 수 있다는 의미입니다.
    그러면 신고 시점의 상황이 변형되어 사실과 다른 정보를 제공하는 등 혼란을 초래할 수 있습니다.
    따라서 신고가 접수되고 확인된 후에는 내용을 수정할 수 없도록 하는 것이 일반적입니다.

    2. 중요한 사안에 대한 신뢰성 유지: 신고는 종종 심각한 문제나 위협을 보고하는 수단입니다.
    예를 들어, 범죄 신고, 폭력적인 행동 신고, 사회적으로 민감한 내용 신고 등이 있습니다.
    이러한 신고는 신속하고 정확한 조치를 필요로 하며, 이를 위해서는 신고한 내용이 수정되거나 철회되지 않아야 합니다.

    3. 남용 방지: 수정이나 철회가 가능하다면, 악의적인 사용자가 신고를 남기고 다른 사람을 괴롭히거나 혼란을 일으킬 수 있습니다.
    이를 막기 위해 일단 신고가 접수되면 수정이나 철회가 불가능하게 하는 것이 좋은 방법입니다.
     */

    private final ReportService reportService;

    /**
     * Create Report API
     * @param postId
     * @param create
     * @return ResponseStatus.SUCCESS_CREATE + Void
     */
    @PostMapping("/report/{postId}")
    public ResponseFormat<Void> createReport(@AuthenticationPrincipal UserDetails userDetails,
                                             @PathVariable Long postId,
                                             @RequestBody @Valid ReportRequestDto.CREATE create) {
        reportService.createReport(userDetails.getUsername(),postId, create);

        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    /**
     * Read Report API - postId 값으로 다중 조회
     * @param postId
     * @return ResponseStatus.SUCCESS_OK + List<ReportResponseDto.READ>
     */
    @GetMapping("/report/{postId}")
    public ResponseFormat<List<ReportResponseDto.READ>> getAllReportsByPost(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportService.getAllReportsByPost(postId));
    }

    /**
     * Read Report API - reportId 값으로 단일 조회
     * @param postId
     * @param reportId
     * @return ResponseStatus.SUCCESS_OK + ReportResponseDto.READ
     */
    @GetMapping("/report/{postId}/{reportId}")
    public ResponseFormat<ReportResponseDto.READ> getReportDetail(@PathVariable Long postId, @PathVariable Long reportId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportService.getReportDetail(reportId));
    }

    /**
     * Read Report API - 모든 Report 다중 조회
     * @return ResponseStatus.SUCCESS_OK + List<ReportResponseDto.READ>
     */
    @GetMapping("/report")
    public ResponseFormat<List<ReportResponseDto.READ>> getAllReports() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportService.getAllReports());
    }

    /**
     * Read Report API - 검색 조건에 맞는 Report 다중 조회
     * @param condition
     * @return ResponseStatus.SUCCESS_OK + List<ReportResponseDto.READ>
     */
    @GetMapping("/report/search")
    public ResponseFormat<List<ReportResponseDto.READ>> getSearchReports(@RequestBody @Valid ReportRequestDto.CONDITION condition) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, reportService.getSearchReports(condition));
    }
}

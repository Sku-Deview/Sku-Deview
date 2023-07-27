package kr.co.skudeview.controller;

import jakarta.validation.Valid;
import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.CompanyService;
import kr.co.skudeview.service.dto.request.CompanyRequestDto;
import kr.co.skudeview.service.dto.response.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CompanyApiController {

    private final CompanyService companyService;

    /**
     * Create API - Member에 Company 정보 추가
     * @param memberId
     * @param company
     * @return ResponseStatus.SUCCESS_CREATE + Void
     */
    @PostMapping("/company/{memberId}")
    public ResponseFormat<Void> createCompany(@PathVariable(name = "memberId") Long memberId, @RequestBody @Valid CompanyRequestDto.CREATE company) {
        companyService.createCompany(memberId, company);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    /**
     * Update API - Member에 작성했던 Company 정보 수정
     * @param memberId
     * @param company
     * @return ResponseStatus.SUCCESS_NO_CONTENT + Void
     */
    @PutMapping("/company/{memberId}")
    public ResponseFormat<Void> updateCompany(@PathVariable(name = "memberId") Long memberId, @RequestBody @Valid CompanyRequestDto.UPDATE company) {
        companyService.updateCompany(memberId, company);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    /**
     * Delete API - Member에 작성했던 Company 정보 삭제
     * @param memberId
     * @param companyId
     * @return ResponseStatus.SUCCESS_CREATE + Void
     */
    @DeleteMapping("/company/{memberId}/{companyId}")
    public ResponseFormat<Void> deleteCompany(@PathVariable(name = "memberId") Long memberId, @PathVariable(name = "companyId") Long companyId) {
        companyService.deleteCompany(memberId, companyId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    /**
     * Get(Read) API - memberId에 해당하는 Company 정보 다중 조회
     * @param memberId
     * @return ResponseStatus.SUCCESS_OK + List<CompanyResponseDto.READ>
     */
    @GetMapping("/company/{memberId}")
    public ResponseFormat<List<CompanyResponseDto.READ>> getCompaniesByMember(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, companyService.getCompaniesByMember(memberId));
    }

    /**
     * Get(Read) API - memberId에 해당하는 CompanyId 정보 단일 조회
     * @param memberId
     * @param companyId
     * @return ResponseStatus.SUCCESS_OK + CompanyResponseDto.DETAIL
     */
    @GetMapping("/company/{memberId}/{companyId}")
    public ResponseFormat<CompanyResponseDto.DETAIL> getCompanyDetail(@PathVariable(name = "memberId") Long memberId, @PathVariable(name = "companyId") Long companyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, companyService.getCompanyDetail(memberId, companyId));
    }

    /**
     * Get(Read) API - 검색 조건에 맞는 Company에 해당하는 Member 다중 조회
     * @param condition
     * @return ResponseStatus.SUCCESS_OK + List<CompanyResponseDto.DETAIL>
     */
    @GetMapping("/company/search")
    public ResponseFormat<List<CompanyResponseDto.DETAIL>> getSearchCompanies(@RequestBody @Valid CompanyRequestDto.CONDITION condition) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, companyService.getSearchCompanies(condition));
    }

}

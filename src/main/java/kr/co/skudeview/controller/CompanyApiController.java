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

    @PostMapping("/company/{memberId}")
    public ResponseFormat<Void> createCompany(@PathVariable(name = "memberId") Long memberId, @RequestBody @Valid CompanyRequestDto.CREATE company) {
        companyService.createCompany(memberId, company);
        return ResponseFormat.success(ResponseStatus.SUCCESS_CREATE);
    }

    @PutMapping("/company/{memberId}")
    public ResponseFormat<Void> updateCompany(@PathVariable(name = "memberId") Long memberId, @RequestBody @Valid CompanyRequestDto.UPDATE company) {
        companyService.updateCompany(memberId, company);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    @DeleteMapping("/company/{memberId}/{companyId}")
    public ResponseFormat<Void> deleteCompany(@PathVariable(name = "memberId") Long memberId, @PathVariable(name = "companyId") Long companyId) {
        companyService.deleteCompany(memberId, companyId);
        return ResponseFormat.success(ResponseStatus.SUCCESS_NO_CONTENT);
    }

    @GetMapping("/company/{memberId}")
    public ResponseFormat<List<CompanyResponseDto.READ>> getCompaniesByMember(@PathVariable(name = "memberId") Long memberId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, companyService.getCompaniesByMember(memberId));
    }

    @GetMapping("/company/{memberId}/{companyId}")
    public ResponseFormat<CompanyResponseDto.DETAIL> getCompanyDetail(@PathVariable(name = "memberId") Long memberId, @PathVariable(name = "companyId") Long companyId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, companyService.getCompanyDetail(memberId, companyId));
    }
}

package kr.co.skudeview.domain.career.service;

import kr.co.skudeview.domain.career.dto.CompanyRequestDto;
import kr.co.skudeview.domain.career.dto.CompanyResponseDto;
import kr.co.skudeview.domain.career.entity.Company;
import kr.co.skudeview.domain.member.entity.Member;

import java.util.List;

public interface CompanyService {

    void createCompany(Long memberId, CompanyRequestDto.CREATE company);

    List<CompanyResponseDto.READ> getCompaniesByMember(Long memberId);

    CompanyResponseDto.DETAIL getCompanyDetail(Long memberId, Long companyId);

    List<CompanyResponseDto.DETAIL> getSearchCompanies(CompanyRequestDto.CONDITION condition);

    void updateCompany(Long memberId, CompanyRequestDto.UPDATE company);

    void deleteCompany(Long memberId, Long companyId);

    default Company toEntity(CompanyRequestDto.CREATE create, Member member) {
        return Company.builder()
                .member(member)
                .companyName(create.getCompanyName())
                .position(create.getPosition())
                .startDate(create.getStartDate())
                .endDate(create.getEndDate())
                .description(create.getDescription())
                .build();
    }

    default CompanyResponseDto.READ toReadDto(Company company) {
        return CompanyResponseDto.READ.builder()
                .companyId(company.getId())
                .memberId(company.getMember().getId())
                .companyName(company.getCompanyName())
                .startDate(company.getStartDate())
                .endDate(company.getEndDate())
                .build();
    }

    default CompanyResponseDto.DETAIL toDetailDto(Company company) {
        return CompanyResponseDto.DETAIL.builder()
                .companyId(company.getId())
                .memberId(company.getMember().getId())
                .companyName(company.getCompanyName())
                .position(company.getPosition())
                .startDate(company.getStartDate())
                .endDate(company.getEndDate())
                .description(company.getDescription())
                .build();
    }
}

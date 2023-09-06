package kr.co.skudeview.domain.career.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.skudeview.domain.career.dto.CompanyRequestDto;
import kr.co.skudeview.domain.career.entity.Company;
import kr.co.skudeview.domain.career.entity.QCompany;
import kr.co.skudeview.domain.member.entity.QMember;
import kr.co.skudeview.global.util.DynamicQueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanySearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QCompany company = QCompany.company;

    private final QMember member = QMember.member;

    public List<Company> find(CompanyRequestDto.CONDITION condition) {
        return queryFactory
                .selectFrom(company)
                .join(member).fetchJoin()
                .where(
                        DynamicQueryUtils.filter(condition.getCompanyIds(), company.id::in),
                        DynamicQueryUtils.filter(condition.getMemberId(), company.member.id::eq),
                        DynamicQueryUtils.filter(condition.getCompanyName(), company.companyName::like),
                        DynamicQueryUtils.filter(condition.getPosition(), company.position::eq),
                        company.deleteAt.eq(Boolean.FALSE)
                )
                .fetch();
    }

}

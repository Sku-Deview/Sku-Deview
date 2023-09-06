package kr.co.skudeview.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.skudeview.domain.member.dto.MemberRequestDto;
import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.domain.member.entity.QMember;
import kr.co.skudeview.global.common.Gender;
import kr.co.skudeview.global.util.DynamicQueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QMember member = QMember.member;

    public List<Member> find(MemberRequestDto.CONDITION condition) {
        return queryFactory
                .selectFrom(member)
                .where(
                        DynamicQueryUtils.filter(condition.getMemberIds(), member.id::in),
                        DynamicQueryUtils.filter(condition.getEmail(), member.email::eq),
                        DynamicQueryUtils.filter(condition.getName(), member.name::eq),
                        DynamicQueryUtils.filter(condition.getNickname(), member.nickname::eq),
                        DynamicQueryUtils.filter(condition.getTelephone(), member.telephone::eq),
                        memberBirthdayBetween(condition.getFromBirthDate(), condition.getToBirthDate()),
                        memberGenderEq(condition.getGender()),
                        member.deleteAt.eq(Boolean.FALSE)
                )
                .fetch();
    }

    private BooleanExpression memberBirthdayBetween(LocalDate fromBirthDate, LocalDate toBirthDate) {
        if (fromBirthDate == null && toBirthDate == null) {
            return null;
        }

        return member.birthDate.between(fromBirthDate, toBirthDate);
    }

    private BooleanExpression memberGenderEq(String gender) {
        if (!StringUtils.hasText(gender)) {
            return null;
        }

        return member.gender.eq(Gender.valueOf(gender));
    }

}

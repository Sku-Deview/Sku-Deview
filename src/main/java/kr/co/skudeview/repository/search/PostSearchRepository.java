package kr.co.skudeview.repository.search;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.co.skudeview.domain.Post;
import kr.co.skudeview.domain.QMember;
import kr.co.skudeview.domain.QPost;
import kr.co.skudeview.infra.util.DynamicQueryUtils;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostSearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QPost post = QPost.post;

    private final QMember member = QMember.member;

    public List<Post> find(PostRequestDto.CONDITION condition) {
        return queryFactory
                .selectFrom(post)
                .join(member).fetchJoin()
                .where(
                        DynamicQueryUtils.filter(condition.getPostIds(), post.id::in),
                        DynamicQueryUtils.filter(condition.getTitle(), post.title::like),
                        DynamicQueryUtils.filter(condition.getPostCategory(), post.postCategory::eq),
                        DynamicQueryUtils.filter(condition.getWriterEmail(), post.member.email::eq),
                        DynamicQueryUtils.filter(condition.getWriterName(), post.member.name::eq),
                        DynamicQueryUtils.filter(condition.getWriterNickname(), post.member.nickname::eq),
                        postDateBetween(condition.getFromPostDate(), condition.getToPostDate()),
                        post.deleteAt.eq(Boolean.FALSE)
                )
                .fetch();
    }

    public Page<Post> findWithPaging(PostRequestDto.CONDITION condition, Pageable pageable) {

        // 조건에 맞는 쿼리 구성
        JPAQuery<Post> query = queryFactory
                .selectFrom(post)
                .leftJoin(post.member, member)
                .where(
                        DynamicQueryUtils.filter(condition.getPostIds(), post.id::in),
                        DynamicQueryUtils.filter(condition.getTitle(), post.title::like),
                        DynamicQueryUtils.filter(condition.getPostCategory(), post.postCategory::eq),
                        DynamicQueryUtils.filter(condition.getWriterEmail(), post.member.email::eq),
                        DynamicQueryUtils.filter(condition.getWriterName(), post.member.name::eq),
                        DynamicQueryUtils.filter(condition.getWriterNickname(), post.member.nickname::eq),
                        postDateBetween(condition.getFromPostDate(), condition.getToPostDate()),
                        post.deleteAt.eq(Boolean.FALSE)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        QueryResults<Post> results = query.fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression postDateBetween(LocalDate fromPostDate, LocalDate toPostDate) {
        if (fromPostDate == null && toPostDate == null) {
            return null;
        }

        return post.regDate.between(fromPostDate.atStartOfDay(), toPostDate.atTime(LocalTime.MAX));

    }
}

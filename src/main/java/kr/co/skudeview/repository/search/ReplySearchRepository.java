package kr.co.skudeview.repository.search;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.skudeview.domain.QMember;
import kr.co.skudeview.domain.QPost;
import kr.co.skudeview.domain.QReply;
import kr.co.skudeview.domain.Reply;
import kr.co.skudeview.infra.util.DynamicQueryUtils;
import kr.co.skudeview.service.dto.request.ReplyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReplySearchRepository {

    private final JPAQueryFactory queryFactory;

    private final QReply reply = QReply.reply;

    private final QPost post = QPost.post;

    private final QMember member = QMember.member;

    public List<Reply> find(ReplyRequestDto.CONDITION condition) {
        return queryFactory
                .selectFrom(reply)
                .join(post).fetchJoin()
                .join(member).fetchJoin()
                .where(
                        DynamicQueryUtils.filter(condition.getReplyIds(), reply.id::in),
                        DynamicQueryUtils.filter(condition.getWritePostId(), reply.post.id::eq),
                        DynamicQueryUtils.filter(condition.getWritePostTitle(), reply.post.title::like),
                        DynamicQueryUtils.filter(condition.getReplyEmail(), reply.member.email::eq),
                        DynamicQueryUtils.filter(condition.getReplyName(), reply.member.name::eq),
                        DynamicQueryUtils.filter(condition.getReplyNickname(), reply.member.nickname::eq),
                        replyDateBetween(condition.getFromReplyDate(), condition.getToReplyDate()),
                        reply.deleteAt.eq(Boolean.FALSE)
                )
                .fetch();
    }

    private BooleanExpression replyDateBetween(LocalDate fromReplyDate, LocalDate toReplyDate) {
        if (fromReplyDate == null && toReplyDate == null) {
            return null;
        }

        return reply.regDate.between(fromReplyDate.atStartOfDay(), toReplyDate.atStartOfDay());
    }
}

package kr.co.skudeview.domain.reply.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.skudeview.domain.member.entity.QMember;
import kr.co.skudeview.domain.member.entity.QMemberLikeReply;
import kr.co.skudeview.domain.post.entity.Post;
import kr.co.skudeview.domain.post.entity.QPost;
import kr.co.skudeview.domain.reply.dto.ReplyRequestDto;
import kr.co.skudeview.domain.reply.entity.QReply;
import kr.co.skudeview.domain.reply.entity.Reply;
import kr.co.skudeview.global.util.DynamicQueryUtils;
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

    private final QMemberLikeReply memberLikeReply = QMemberLikeReply.memberLikeReply;

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

    public List<Reply> findRepliesByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(reply)
                .join(member).fetchJoin()
                .where(
                        reply.member.id.eq(memberId),
                        reply.deleteAt.eq(Boolean.FALSE)
                )
                .fetch();
    }

    public List<Reply> findLikeRepliesByMemberId(Long memberId) {
        return queryFactory
                .selectFrom(reply)
                .join(memberLikeReply).on(memberLikeReply.reply.eq(reply))
                .join(member).on(memberLikeReply.member.eq(member))
                .fetchJoin()
                .where(
                        member.id.eq(memberId)
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

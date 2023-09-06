package kr.co.skudeview.domain.member.entity;

import jakarta.persistence.*;
import kr.co.skudeview.global.common.BaseEntity;
import kr.co.skudeview.domain.reply.entity.Reply;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLikeReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberLikeReply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @Builder
    public MemberLikeReply(Member member,
                           Reply reply) {
        this.member = member;
        this.reply = reply;
    }

}

package kr.co.skudeview.domain.reply.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.domain.post.entity.Post;
import kr.co.skudeview.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "like_count", columnDefinition = "INTEGER DEFAULT 0")
    private int likeCount;

    @Builder
    public Reply(Member member,
                 Post post,
                 String content,
                 int likeCount) {
        this.member = member;
        this.post = post;
        this.content = content;
        this.likeCount = likeCount;
    }

//    public void updateReply(String content) {
//        this.content = content;
//    }

    public void addLikeCount() {
        this.likeCount += 1;
    }

}

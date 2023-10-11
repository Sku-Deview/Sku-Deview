package kr.co.skudeview.domain.follow.entity;

import jakarta.persistence.*;
import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member fromMember;  // 팔로우를 요청한 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member toMember;    // 팔로우 당한 사람 (fromMember가 팔로우한 사람)

    @Builder
    public Follow(Member toMember, Member fromMember) {
        this.toMember = toMember;
        this.fromMember = fromMember;
    }
}

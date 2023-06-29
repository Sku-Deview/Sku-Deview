package kr.co.skudeview.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member toMember;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member fromMember;

    @Builder
    public Follow(Member toMember, Member fromMember) {
        this.toMember = toMember;
        this.fromMember = fromMember;
    }
}

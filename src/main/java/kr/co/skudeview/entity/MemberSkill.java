package kr.co.skudeview.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberSkill_id")
    private Long id;

    @JoinColumn(name = "member_id")
    private Member member_id;

    @JoinColumn(name = "skill_id")
    private Skill skill_id;
}

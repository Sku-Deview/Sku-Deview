package kr.co.skudeview.domain;

import jakarta.persistence.*;
import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.University;

@Entity
public class MemberUniversity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;
}

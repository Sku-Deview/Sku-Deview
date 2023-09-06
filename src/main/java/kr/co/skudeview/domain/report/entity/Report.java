package kr.co.skudeview.domain.report.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.domain.post.entity.Post;
import kr.co.skudeview.global.common.ReportCategory;
import kr.co.skudeview.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "report_category")
    @Enumerated(EnumType.STRING)
    private ReportCategory reportCategory;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "description")
    @NotNull
    private String description;

    @Builder
    public Report(Member member,
                  Post post,
                  ReportCategory reportCategory,
                  String title,
                  String description) {
        this.member = member;
        this.post = post;
        this.reportCategory = reportCategory;
        this.title = title;
        this.description = description;
    }
}

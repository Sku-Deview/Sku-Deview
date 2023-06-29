package kr.co.skudeview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class University extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member_id;

    @Column(name = "univ_name")
    @NotNull
    private String univName;

    @Column(name = "major")
    @NotNull
    private String major;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description")
    private String Description;

    @Builder
    public University(Member member_id, String univName, String major, LocalDate startDate, LocalDate endDate, String description) {
        this.member_id = member_id;
        this.univName = univName;
        this.major = major;
        this.startDate = startDate;
        this.endDate = endDate;
        Description = description;
    }
}

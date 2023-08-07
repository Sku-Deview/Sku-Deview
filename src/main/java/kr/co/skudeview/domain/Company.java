package kr.co.skudeview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.service.dto.request.CompanyRequestDto;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "company_name")
    @NotNull
    private String companyName;

    @Column(name = "position")
    @NotNull
    private String position;

    @Column(name = "start_date")
    @NotNull
    private LocalDate startDate;

    @Column(name = "end_date")
    @NotNull
    private LocalDate endDate;

    @Column(name = "description")
    @NotNull
    private String description;

    @Builder
    public Company(Member member,
                   String companyName,
                   String position,
                   LocalDate startDate,
                   LocalDate endDate,
                   String description) {
        this.member = member;
        this.companyName = companyName;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public void updateCompany(CompanyRequestDto.UPDATE update) {
        this.companyName = update.getCompanyName();
        this.position = update.getPosition();
        this.startDate = update.getStartDate();
        this.endDate = update.getEndDate();
        this.description = update.getDescription();
    }
}

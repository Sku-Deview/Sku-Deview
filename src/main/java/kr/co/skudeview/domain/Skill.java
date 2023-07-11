package kr.co.skudeview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.service.dto.request.SkillRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id;

    @Column(name = "skill_name", unique = true)
    @NotNull
    private String name;

    @Builder
    public Skill(String name) {
        this.name = name;
    }

    // modify를 위한 메서드
    public void updateSkill(SkillRequestDto.UPDATE update) {
        this.name = update.getName();
    }
}

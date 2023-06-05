package kr.co.skudeview.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id;

    private ArrayList<String> name = new ArrayList<>();
}

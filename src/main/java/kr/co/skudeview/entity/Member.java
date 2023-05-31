package kr.co.skudeview.entity;

import jakarta.persistence.*;
import kr.co.skudeview.entity.enums.Gender;
import kr.co.skudeview.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String telephone;

    private String address;

    private LocalDate birthDate;

    private Gender gender;

    private Role role;

    @Builder
    public Member(String email,
                  String password,
                  String name,
                  String nickname,
                  String telephone,
                  String address,
                  LocalDate birthDate,
                  Gender gender,
                  Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.telephone = telephone;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
    }

    // modify 위한 메소드
    public void updateMember() {

    }

}

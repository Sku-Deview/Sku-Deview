package kr.co.skudeview.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.entity.enums.Gender;
import kr.co.skudeview.entity.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "nickname", unique = true)
    @NotNull
    private String nickname;

    @Column(name="telephone", unique = true)
    @NotNull
    private String telephone;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "birth_date")
    @NotNull
    private LocalDate birthDate;

    @Column(name = "member_gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
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

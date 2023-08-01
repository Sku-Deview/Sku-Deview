package kr.co.skudeview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.domain.enums.Gender;
import kr.co.skudeview.domain.enums.Role;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "telephone", unique = true)
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

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "member"
    )
    private List<MemberSkill> memberSkills = new ArrayList<>();


    private String univName;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "member"
    )
    private List<Company> companies = new ArrayList<>();

//    @OneToMany(
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.MERGE,CascadeType.PERSIST},
//            mappedBy = "member")
//    List<MemberUniversity> memberUniversities = new ArrayList<>();

    private String major;

    @Builder
    public Member(String email,
                  String password,
                  String name,
                  String nickname,
                  String telephone,
                  String address,
                  LocalDate birthDate,
                  Gender gender,
                  Role role,
                  List<MemberSkill> memberSkills) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.telephone = telephone;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
        this.memberSkills = memberSkills;
    }

    // modify 위한 메소드
    public void updateMember(MemberRequestDto.UPDATE update) {
        this.password = update.getPassword();
        this.name = update.getName();
        this.nickname = update.getNickname();
        this.telephone = update.getTelephone();
        this.address = update.getAddress();
        this.birthDate = update.getBirthDate();
        this.gender = Gender.valueOf(update.getGender());
        this.role = Role.valueOf(update.getRole());
    }

    public void changeMemberSkills(List<MemberSkill> changeMemberSkills) {
        this.memberSkills = changeMemberSkills;
    }

    public void changeUnivMajor(String univName, String major) {
        this.univName = univName;
        this.major = major;
    }

    public void deleteUnivMajor() {
        this.univName = null;
        this.major = null;
    }

}

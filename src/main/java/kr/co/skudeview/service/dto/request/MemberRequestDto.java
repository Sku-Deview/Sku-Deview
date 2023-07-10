package kr.co.skudeview.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class MemberRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {

        private String email;

        private String password;

//        private String confirmPassword;

        private String name;

        private String nickname;

        private String telephone;

        private String address;

        private LocalDate birthDate;

        private String gender;

        private String role;

        private List<String> skillName;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {

        private Long memberId;

        private String password;

//        private String confirmPassword;

        private String name;

        private String nickname;

        private String telephone;

        private String address;

        private LocalDate birthDate;

        private String gender;

        private String role;

        private List<String> skillName;

    }

}

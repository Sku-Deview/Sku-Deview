package kr.co.skudeview.service.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.domain.enums.Gender;
import kr.co.skudeview.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    }

}

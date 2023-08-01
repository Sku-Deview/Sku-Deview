package kr.co.skudeview.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

public class MemberResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {

        private Long memberId;

        private String email;

        private String name;

        private String nickname;

        private String telephone;

        private String address;

        private LocalDate birthDate;

        private String gender;

        private String role;

        private Set<String> skillName;

        private String token;
    }

}

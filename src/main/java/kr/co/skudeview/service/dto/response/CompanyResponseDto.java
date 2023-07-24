package kr.co.skudeview.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class CompanyResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {

        private Long companyId;

        private Long memberId;

        private String companyName;

        private LocalDate startDate;

        private LocalDate endDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DETAIL {

        private Long companyId;

        private Long memberId;

        private String companyName;

        private String position;

        private LocalDate startDate;

        private LocalDate endDate;

        private String description;
    }
}

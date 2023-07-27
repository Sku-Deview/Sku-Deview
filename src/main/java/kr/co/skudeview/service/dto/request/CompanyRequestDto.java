package kr.co.skudeview.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class CompanyRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {

        private Long memberId;

        private String companyName;

        private String position;

        private LocalDate startDate;

        private LocalDate endDate;

        private String description;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {

        private Long companyId;

        private Long memberId;

        private String companyName;

        private String position;

        private LocalDate startDate;

        private LocalDate endDate;

        private String description;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CONDITION {
        private List<Long> companyIds;

        private Long memberId;

        private String companyName;

        private String position;
    }
}

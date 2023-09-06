package kr.co.skudeview.domain.report.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class ReportRequestDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class CREATE {


        private String reportCategory;

        private String title;

        private String description;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class CONDITION {
        private List<Long> reportIds;

        private Long reportPostId;

        private String reportPostTitle;

        private String reportMemberEmail;

        private String reportMemberName;

        private String reportMemberNickname;

        private String title;

        private String reportCategory;

        private LocalDate fromReportDate;

        private LocalDate toReportDate;
    }

}

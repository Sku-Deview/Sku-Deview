package kr.co.skudeview.domain.report.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ReportResponseDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class READ {

        private Long reportId;

        private Long postId;

        private String memberEmail;

        private String memberName;

        private String reportCategory;

        private String postTitle;

        private String title;

        private String description;

        private LocalDateTime regDate;
    }

}

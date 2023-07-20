package kr.co.skudeview.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

        private String postCategory;

        private String postTitle;

        private String title;

        private String description;
    }

}

package kr.co.skudeview.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReportRequestDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class CREATE {

        private Long postId;

        private String memberEmail;

        private String postCategory;

        private String title;

        private String description;
    }

}

package kr.co.skudeview.service.dto.request;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.enums.PostCategory;
import lombok.*;

public class PostRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {
        private Member member;

        private String title;

        private String content;

        private PostCategory postCategory;

        private Integer likeCount;

        private Integer viewCount;


    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {
        private Long postId;

        private String title;

        private String content;

        private PostCategory postCategory;
    }
}

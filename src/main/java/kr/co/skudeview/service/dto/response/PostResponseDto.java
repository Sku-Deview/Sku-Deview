package kr.co.skudeview.service.dto.response;

import kr.co.skudeview.domain.Post;
import kr.co.skudeview.domain.enums.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {
        private Long postId;

        private String memberEmail;

        private String title;

        private String content;

        private PostCategory postCategory;

        private int likeCount;

        private int viewCount;

        public READ(Post post) {
        }
    }

}

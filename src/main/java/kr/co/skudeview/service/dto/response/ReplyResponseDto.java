package kr.co.skudeview.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ReplyResponseDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class READ {
        private Long replyId;

        private String memberEmail;

        private Long postId;

        private String content;

        private int likeCount;
    }

}

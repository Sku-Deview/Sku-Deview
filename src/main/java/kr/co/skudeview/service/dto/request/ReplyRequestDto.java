package kr.co.skudeview.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReplyRequestDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class CREATE {
        private Long postId;

        private String memberEmail;

        private String content;

        private int likeCount;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class UPDATE {
        private Long replyId;

        private String content;


    }

}

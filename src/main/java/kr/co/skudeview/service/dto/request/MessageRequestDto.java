package kr.co.skudeview.service.dto.request;

import lombok.*;


public class MessageRequestDto {
    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class CREATE {
        private String title;

        private String content;

        private String senderName;

        private String receiverName;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class READ {

        private String nickName;
    }
}

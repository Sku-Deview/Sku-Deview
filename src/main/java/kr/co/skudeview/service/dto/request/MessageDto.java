package kr.co.skudeview.service.dto.request;

import lombok.*;


public class MessageDto {
    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class CREATE {
        private String receiverName;

        private String title;

        private String content;


    }
    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class READ {
        private String receiverName;

        private String title;

        private String content;

        private String senderName;


    }



}

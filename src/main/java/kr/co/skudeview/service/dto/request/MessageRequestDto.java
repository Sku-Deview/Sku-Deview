package kr.co.skudeview.service.dto.request;

import lombok.*;

import java.time.LocalDateTime;


public class MessageRequestDto {
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

        private LocalDateTime regDate;


    }



}

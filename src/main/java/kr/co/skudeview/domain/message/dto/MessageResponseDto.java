package kr.co.skudeview.domain.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MessageResponseDto {

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

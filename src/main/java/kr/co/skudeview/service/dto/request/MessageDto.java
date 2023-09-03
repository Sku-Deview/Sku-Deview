package kr.co.skudeview.service.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MessageDto {

    private String title;

    private String content;

    private String senderName;

    private String receiverName;

}

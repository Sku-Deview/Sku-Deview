package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.MessageDto;

import java.util.List;

public interface MessageService {

    MessageDto.READ createMessage(String email, MessageDto.CREATE create);

    // 받은 편지함 불러오기
    List<MessageDto.READ> getReceivedMessages(String email);

    //받은 편지 삭제
    Long deleteMessageByReceiver(Long messageId, String email);

    //보낸 편지함 불러오기
    List<MessageDto.READ> getSendMessages(String email);

    //보낸 편지 삭제
    Long deleteMessageBySender(Long messageId, String email);


}
package kr.co.skudeview.service;

import kr.co.skudeview.service.dto.request.MessageDto;

import java.util.List;

public interface MessageService {

    MessageDto createMessage(MessageDto create);

    // 받은 편지함 불러오기
    List<MessageDto> getReceivedMessages(String email);

    //받은 편지 삭제
    Long deleteMessageByReceiver(Long messageId, String email);

    //보낸 편지함 불러오기
    List<MessageDto> getSendMessages(String email);

    //보낸 편지 삭제
    Long deleteMessageBySender(Long messageId, String email);


}
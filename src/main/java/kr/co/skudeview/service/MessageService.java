package kr.co.skudeview.service;

import kr.co.skudeview.domain.Member;
import kr.co.skudeview.domain.Message;
import kr.co.skudeview.domain.enums.Gender;
import kr.co.skudeview.domain.enums.Role;
import kr.co.skudeview.service.dto.request.MemberRequestDto;
import kr.co.skudeview.service.dto.request.MessageRequestDto;

import java.util.Collections;
import java.util.List;

public interface MessageService {

    MessageRequestDto.CREATE createMessage(MessageRequestDto.CREATE create);

    // 받은 편지함 불러오기
    List<MessageRequestDto.CREATE> getReceivedMessages(String email);

    //받은 편지 삭제
    Long deleteMessageByReceiver(Long messageId, String email);

    //보낸 편지함 불러오기
    List<MessageRequestDto.CREATE> getSendMessages(String email);

    //보낸 편지 삭제
    Long deleteMessageBySender(Long messageId, String email);


}
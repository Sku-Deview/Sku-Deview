package kr.co.skudeview.controller;

import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.MessageService;
import kr.co.skudeview.service.dto.request.MessageDto;
import kr.co.skudeview.service.model.custom.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MessageApiController {

    private final MessageService messageService;

    @PostMapping("/message")
    public MessageDto.READ createMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @RequestBody MessageDto.CREATE create) {

        MessageDto.READ message = messageService.createMessage(userDetails.getUsername(), create);
        return  message;
    }

    //받은 편지함 확인
    @GetMapping("/message/received")
    public List<MessageDto.READ> getReceivedMessages(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MessageDto.READ> result = messageService.getReceivedMessages(userDetails.getUsername());

        return  result;
    }

    //받은 쪽지 삭제
    @DeleteMapping("/message/received/{messageId}")
    public ResponseFormat<Long> deleteReceivedMessage(@PathVariable("messageId") Long messageId,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long deleteMessageId = messageService.deleteMessageByReceiver(messageId, userDetails.getUsername());

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, deleteMessageId);
    }

    //보낸 편지함 확인
    @GetMapping("/message/send")
    public List<MessageDto.READ> getSendMessage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MessageDto.READ> result = messageService.getSendMessages(userDetails.getUsername());
        return result;
    }

    //보낸 쪽지 삭제
    @DeleteMapping("/message/send/{messageId}")
    public ResponseFormat<Long> deleteSendMessage(@PathVariable("messageId") Long messageId,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long deleteMessageId = messageService.deleteMessageBySender(messageId, userDetails.getUsername());
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, deleteMessageId);
    }

}

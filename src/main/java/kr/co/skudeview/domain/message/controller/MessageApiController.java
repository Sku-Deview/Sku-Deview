package kr.co.skudeview.domain.message.controller;

import kr.co.skudeview.domain.message.dto.MessageRequestDto;
import kr.co.skudeview.domain.message.service.MessageService;
import kr.co.skudeview.global.model.ResponseFormat;
import kr.co.skudeview.global.model.ResponseStatus;
import kr.co.skudeview.global.auth.CustomUserDetails;
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
    public ResponseFormat<MessageRequestDto.READ>  createMessage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @RequestBody MessageRequestDto.CREATE create) {

        MessageRequestDto.READ message = messageService.createMessage(userDetails.getUsername(), create);
        return  ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,message);
    }

    //받은 편지함 확인
    @GetMapping("/message/received")
    public ResponseFormat<List<MessageRequestDto.READ>> getReceivedMessages(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MessageRequestDto.READ> result = messageService.getReceivedMessages(userDetails.getUsername());

        return  ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,result);
    }

    //받은 쪽지 삭제
    @DeleteMapping("/message/received/{messageId}")
    public ResponseFormat<Long> deleteReceivedMessage(@PathVariable("messageId") Long messageId,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long deleteMessageId = messageService.deleteMessageByReceiver(messageId, userDetails.getUsername());

        return ResponseFormat.successWithData(kr.co.skudeview.global.model.ResponseStatus.SUCCESS_OK, deleteMessageId);
    }

    //보낸 편지함 확인
    @GetMapping("/message/send")
    public ResponseFormat<List<MessageRequestDto.READ>> getSendMessage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MessageRequestDto.READ> result = messageService.getSendMessages(userDetails.getUsername());
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK,result);
    }

    //보낸 쪽지 삭제
    @DeleteMapping("/message/send/{messageId}")
    public ResponseFormat<Long> deleteSendMessage(@PathVariable("messageId") Long messageId,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long deleteMessageId = messageService.deleteMessageBySender(messageId, userDetails.getUsername());
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, deleteMessageId);
    }

}

package kr.co.skudeview.controller;

import kr.co.skudeview.infra.model.ResponseFormat;
import kr.co.skudeview.infra.model.ResponseStatus;
import kr.co.skudeview.service.MessageService;
import kr.co.skudeview.service.dto.request.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MessageApiController {

    private final MessageService messageService;

    @PostMapping("/message")
    public ResponseFormat<MessageRequestDto.CREATE> createMessage(@RequestBody MessageRequestDto.CREATE create) {
        log.info("create.receiveNickName ={}", create.getReceiverName());
        log.info("create.sendNickName ={}", create.getSenderName());
        MessageRequestDto.CREATE message = messageService.createMessage(create);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, message);
    }

    //받은 편지함 확인
    @GetMapping("/message/received")
    public ResponseFormat<List<MessageRequestDto.CREATE>> getReceivedMessages(@RequestBody MessageRequestDto.READ nickName) {
        List<MessageRequestDto.CREATE> result = messageService.getReceivedMessages(nickName);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, result);
    }

    //받은 쪽지 삭제
    @DeleteMapping("/message/received/{messageId}")
    public ResponseFormat<Long> deleteReceivedMessage(@PathVariable("messageId") Long messageId,
                                                      @RequestBody MessageRequestDto.READ nickName) {
        Long deleteMessageId = messageService.deleteMessageByReceiver(messageId, nickName);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, deleteMessageId);
    }

    //보낸 편지함 확인
    @GetMapping("/message/send")
    public ResponseFormat<List<MessageRequestDto.CREATE>> getSendMessage(@RequestBody MessageRequestDto.READ nickName) {
        List<MessageRequestDto.CREATE> result = messageService.getSendMessages(nickName);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, result);
    }

    //보낸 쪽지 삭제
    @DeleteMapping("/message/send/{messageId}")
    public ResponseFormat<Long> deleteSendMessage(@PathVariable("messageId") Long messageId,
                                                  @RequestBody MessageRequestDto.READ nickName) {
        Long deleteMessageId = messageService.deleteMessageBySender(messageId, nickName);
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, deleteMessageId);
    }

}

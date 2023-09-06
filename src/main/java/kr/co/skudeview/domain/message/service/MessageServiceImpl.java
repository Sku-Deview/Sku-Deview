package kr.co.skudeview.domain.message.service;

import kr.co.skudeview.domain.member.entity.Member;
import kr.co.skudeview.domain.message.entity.Message;
import kr.co.skudeview.domain.member.repository.MemberRepository;
import kr.co.skudeview.domain.message.repository.MessageRepository;
import kr.co.skudeview.domain.message.dto.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public MessageRequestDto.READ createMessage(String email, MessageRequestDto.CREATE create) {
        Optional<Member> receiver = memberRepository.findMemberByNicknameAndDeleteAtFalse(create.getReceiverName());
        Optional<Member> sender = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        Message message = toEntity(create, receiver.get(), sender.get());
        messageRepository.save(message);

        return toDto(message);
    }


    // 받은 편지함 불러오기
    // 한 명의 유저가 받은 모든 메시지
    @Transactional(readOnly = true)
    @Override
    public List<MessageRequestDto.READ> getReceivedMessages(String email) {
        return memberRepository.findMemberByEmailAndDeleteAtFalse(email)
                .map(findMember -> messageRepository.findAllByReceiver_Nickname(findMember.getNickname()))
                .orElse(Collections.emptyList())
                .stream()
                .filter(message -> !message.isDeletedByReceiver())
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    // 받은 편지 삭제(상대방으로부터 받은)
    @Transactional
    @Override
    public Long deleteMessageByReceiver(Long messageId, String email) {
        Optional<Message> message = messageRepository.findById(messageId);
        Optional<Member> member = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        if (member.get().getNickname().equals(message.get().getSender().getNickname())) {    //상대방의 닉네임과 messageId 체크 후
            message.get().deleteByReceiver(); //받은 사람에게 메시지 삭제
        }
        return message.get().getId();

    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageRequestDto.READ> getSendMessages(String email) {
        return memberRepository.findMemberByEmailAndDeleteAtFalse(email)
                .map(findMember -> messageRepository.findAllBySender_Nickname(findMember.getNickname()))
                .orElse(Collections.emptyList())
                .stream()
                .filter(message -> !message.isDeletedBySender())
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    //보낸 편지 삭제
    @Transactional
    @Override
    public Long deleteMessageBySender(Long messageId, String email) {
        Optional<Message> message = messageRepository.findById(messageId);
        Optional<Member> member = memberRepository.findMemberByEmailAndDeleteAtFalse(email);

        if (member.get().getNickname().equals(message.get().getSender().getNickname())) {
            message.get().deleteBySender();
        }
        return message.get().getId();
    }

    private static Message toEntity(MessageRequestDto.CREATE create, Member receiver, Member sender) {
        return Message.builder()
                .receiver(receiver)
                .sender(sender)
                .title(create.getTitle())
                .content(create.getContent())
                .deletedByReceiver(false)
                .deletedBySender(false)
                .build();
    }

    private MessageRequestDto.READ toDto(Message message) {
        return MessageRequestDto.READ.builder()
                .receiverName(message.getReceiver().getNickname())
                .senderName(message.getSender().getNickname())
                .content(message.getContent())
                .title(message.getTitle())
                .regDate(message.getRegDate())
                .build();
    }

}

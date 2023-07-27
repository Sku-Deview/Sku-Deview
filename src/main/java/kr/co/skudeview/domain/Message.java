package kr.co.skudeview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    //@JoinColumn(name = "sender_id", referencedColumnName = "member_id", insertable = false, updatable = false)
   // @OnDelete(action = OnDeleteAction.NO_ACTION)    //작성자가 계정을 삭제하면 함께 지우기 위함
    @JoinColumn(name = "sender", referencedColumnName = "nickname")
    private Member sender;


    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
   // @OnDelete(action = OnDeleteAction.NO_ACTION)    //수신자가 계정을 삭제하면 함께 지우기 위함
    //@JoinColumn(name = "receiver_id", referencedColumnName = "nickname")
    @JoinColumn(name = "receiver", referencedColumnName = "nickname")
    private Member receiver;

    @Column(nullable = false)
    private boolean deletedBySender;

    @Column(nullable = false)
    private boolean deletedByReceiver;
    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "title")
    @NotNull
    private String title;
    @Builder
    public Message(Member sender, Member receiver, boolean deletedBySender, boolean deletedByReceiver, String content, String title) {
        this.sender = sender;
        this.receiver = receiver;
        this.deletedBySender = deletedBySender;
        this.deletedByReceiver = deletedByReceiver;
        this.content = content;
        this.title = title;
        log.info("message.this.receiver = {}",this.receiver.getNickname());
        log.info("message.this.sender = {}",this.sender.getNickname());
    }

    public void deleteBySender() {
        this.deletedBySender = true;
    }

    public void deleteByReceiver() {
        this.deletedByReceiver = true;
    }

    /**
     * isDeleted 메소드는 deleteBySender&Receiver 의 값이 둘다 true이면 true를 반환해줍니다
     */
//    public boolean isDeleted() {
//        return isDeletedBySender() && isDeletedByReceiver();
//    }
}

package kr.co.skudeview.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member sender;


    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", insertable = false, updatable = false)
    private Member receiver;

    @Column(name = "content")
    @NotNull
    private String content;

    @Builder
    public Message(Member sender, Member receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }
}

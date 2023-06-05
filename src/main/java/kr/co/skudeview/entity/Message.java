package kr.co.skudeview.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @JoinColumn(name = "member_id")
    private Member sender;

    @JoinColumn(name = "member_id")
    private Member receiver;

    @Column(name = "content")
    @NotNull
    private String content;
}

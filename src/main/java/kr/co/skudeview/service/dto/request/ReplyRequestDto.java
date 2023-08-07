package kr.co.skudeview.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class ReplyRequestDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class CREATE {

        private String content;

        private int likeCount;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class UPDATE {
        private Long replyId;

        private String content;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class CONDITION {
        private List<Long> replyIds;

        private Long writePostId;

        private String writePostTitle;

        private String replyEmail;

        private String replyName;

        private String replyNickname;

        private LocalDate fromReplyDate;

        private LocalDate toReplyDate;
    }

}

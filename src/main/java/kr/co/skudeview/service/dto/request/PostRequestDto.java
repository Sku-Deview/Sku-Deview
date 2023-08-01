package kr.co.skudeview.service.dto.request;

import kr.co.skudeview.domain.enums.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public class PostRequestDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CREATE {
        private String memberEmail;

        private String title;

        private String content;

        private PostCategory postCategory;

        private Integer likeCount;

        private Integer viewCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UPDATE {
        private Long postId;

        private String title;

        private String content;

        private PostCategory postCategory;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CONDITION {
        private List<Long> postIds;

        private String title;

        private PostCategory postCategory;

        private String writerEmail;

        private String writerName;

        private String writerNickname;

        private LocalDate fromPostDate;

        private LocalDate toPostDate;

        private Pageable pageable;

    }
}

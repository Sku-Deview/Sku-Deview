package kr.co.skudeview.domain.post.dto;

import kr.co.skudeview.domain.post.entity.Post;
import kr.co.skudeview.global.common.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class PostResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class READ {
        private Long postId;

        private String memberEmail;

        private String memberNickname;

        private String title;

        private String content;

        private PostCategory postCategory;

        private int likeCount;

        private int viewCount;

        private MultipartFile boardFile; // save.html -> Controller 파일 담는 용도

        private String originalFileName; // 원본 파일 이름

        private String storedFileName; // 서버 저장용 파일 이름

        private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

        private boolean deleteAt;

        private LocalDateTime regDate;

        public READ(Post post) {
        }
    }

}

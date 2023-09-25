package kr.co.skudeview.domain.post.dto;

import kr.co.skudeview.domain.file.entity.FileFormat;
import kr.co.skudeview.domain.post.entity.Post;
import kr.co.skudeview.global.common.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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

        private int replyCount;

        private List<FileFormat> fileFormat;

        private boolean deleteAt;

        private LocalDateTime regDate;

        public READ(Post post) {
        }
    }

}

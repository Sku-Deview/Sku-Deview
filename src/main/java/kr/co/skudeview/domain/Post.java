package kr.co.skudeview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.domain.enums.PostCategory;
import kr.co.skudeview.service.dto.request.PostRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "post_category")
    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "view_count")
    private int viewCount;

    @Builder
    public Post(Member member,
                String title,
                String content,
                int likeCount,
                int viewCount,
                PostCategory postCategory) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.postCategory = postCategory;
    }

    //modify를 위한 method
    public void updatePost(String title, String content, PostCategory postCategory) {
        this.title = title;
        this.content = content;
        this.postCategory= postCategory;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    /**
     * 게시글 삭제
     */
    public void delete() {
       this.setDeleteAt("Y");
        }
}

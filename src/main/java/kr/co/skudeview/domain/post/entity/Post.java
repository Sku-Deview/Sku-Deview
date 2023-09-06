package kr.co.skudeview.domain.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.co.skudeview.global.common.PostCategory;
import kr.co.skudeview.global.common.BaseEntity;
import kr.co.skudeview.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    @NotNull
    private String content;

    @Column(name = "post_category")
    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "view_count")
    private int viewCount;

    @Column
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostFile> postFileList = new ArrayList<>();

    @Builder
    public Post(Member member,
                String title,
                String content,
                int likeCount,
                int viewCount,
                int fileAttached,
                PostCategory postCategory) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.postCategory = postCategory;
        this.fileAttached = fileAttached;
    }


    //modify를 위한 method
    public void updatePost(String title, String content, PostCategory postCategory) {
        this.title = title;
        this.content = content;
        this.postCategory = postCategory;
    }

    public void addViewCount(Long viewCnt) {
        this.viewCount = viewCnt.intValue();
    }

    public void addLikeCount(Long likeCnt) {
        this.likeCount = likeCnt.intValue();
    }
}

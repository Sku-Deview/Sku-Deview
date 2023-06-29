package kr.co.skudeview.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "path")
    @NotNull
    private String path;

    @Column(name = "size")
    @NotNull
    private String size;

    @Builder
    public Image(Post post,
                 String name,
                 String path,
                 String size) {
        this.post = post;
        this.name = name;
        this.path = path;
        this.size = size;
    }
}

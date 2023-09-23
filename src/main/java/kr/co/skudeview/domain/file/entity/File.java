package kr.co.skudeview.domain.file.entity;

import jakarta.persistence.*;
import kr.co.skudeview.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@NoArgsConstructor
@Getter
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String fileUrl;

    @Column
    private String fileExtension;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public File(MultipartFile validatedFile, Post post) {
        this.fileName = validatedFile.getOriginalFilename();
        this.fileUrl = "http://localhost:3000/api/v1/files/" + post.getId() + "_" + this.fileName;
        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        this.post = post;
    }

}

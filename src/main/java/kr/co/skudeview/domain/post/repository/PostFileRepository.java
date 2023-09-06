package kr.co.skudeview.domain.post.repository;

import kr.co.skudeview.domain.post.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {
}
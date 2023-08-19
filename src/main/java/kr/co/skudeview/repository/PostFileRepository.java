package kr.co.skudeview.repository;

import kr.co.skudeview.domain.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {
}

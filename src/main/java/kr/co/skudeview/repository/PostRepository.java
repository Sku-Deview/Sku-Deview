package kr.co.skudeview.repository;

import kr.co.skudeview.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}

package kr.co.skudeview.repository;

import kr.co.skudeview.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    List<Reply> findAllByPostId(Long postId);
}

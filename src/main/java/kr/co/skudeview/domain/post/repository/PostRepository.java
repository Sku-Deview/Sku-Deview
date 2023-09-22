package kr.co.skudeview.domain.post.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.skudeview.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // postId 값으로 post 단일 조회 + 삭제되지 않은 post
    Optional<Post> findPostByIdAndDeleteAtFalse(Long id);

    // post 다중 조회 + 삭제되지 않은 post
    List<Post> findAllByDeleteAtFalse();

    // memberEmail 로 member 가 작성한 post 다중 조회 + 삭제되지 않은 post
    List<Post> findPostsByMemberEmailAndDeleteAtFalse(String memberEmail);

    // memberEmail 로 member 가 작성한 post 다중 조회
    List<Post> findPostsByMemberId(Long memberId);

    Optional<Post> findPostById(Long postId);

    @Query("SELECT COUNT(r) FROM Reply r WHERE r.post.id = :postId")
    int countRepliesByPostId(@Param("postId") Long postId);
}

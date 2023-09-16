package kr.co.skudeview.domain.reply.repository;

import kr.co.skudeview.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // replyId 값으로 reply 단일 조회 + 삭제되지 않은 reply
    Optional<Reply> findReplyByIdAndDeleteAtFalse(Long id);

    // reply 다중 조회 (작성자, 게시글 상관없이) + 삭제되지 않은 reply
    List<Reply> findAllByDeleteAtFalse();

    // postId 값으로 reply 다중 조회 (게시글) + 삭제되지 않은 reply
    List<Reply> findRepliesByPostIdAndDeleteAtFalse(Long postId);

    // memberEmail 값으로 reply 다중 조회 (작성자) + 삭제되지 않은 reply
    List<Reply> findRepliesByMemberEmailAndDeleteAtFalse(String memberEmail);

    // memberEmail 값으로 reply 다중 조회 (작성자)
    List<Reply> findRepliesByMemberEmail(String memberEmail);

    Optional<Reply> findReplyById(Long replyId);
}

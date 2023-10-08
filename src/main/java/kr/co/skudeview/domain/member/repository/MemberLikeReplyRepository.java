package kr.co.skudeview.domain.member.repository;

import jakarta.persistence.Id;
import kr.co.skudeview.domain.member.entity.MemberLikeReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLikeReplyRepository extends JpaRepository<MemberLikeReply, Long> {

    boolean existsMemberLikeReplyByReply_IdAndMember_IdAndDeleteAtFalse(Long replyId, Long memberId);
}

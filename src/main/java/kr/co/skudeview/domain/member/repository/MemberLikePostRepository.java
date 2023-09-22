package kr.co.skudeview.domain.member.repository;

import kr.co.skudeview.domain.member.entity.MemberLikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberLikePostRepository extends JpaRepository<MemberLikePost, Long> {

    boolean existsMemberLikePostByPost_IdAndMember_IdAndDeleteAtFalse(Long postId, Long memberId);

}

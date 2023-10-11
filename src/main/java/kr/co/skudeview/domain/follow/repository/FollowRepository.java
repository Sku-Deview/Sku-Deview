package kr.co.skudeview.domain.follow.repository;

import kr.co.skudeview.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    // fromMemberNickname, toMemberNickname 이용하여 이미 신고한 것인지 체크
    boolean existsByFromMember_NicknameAndToMember_NicknameAndDeleteAtFalse(String fromMemberNickname, String toMemberNickname);

    Optional<Follow> findFollowByFromMember_NicknameAndToMember_NicknameAndDeleteAtFalse(String fromMemberNickname, String toMemberNickname);

    List<Follow> findFollowsByFromMember_NicknameAndAndDeleteAtFalse(String fromMemberNickname);

    List<Follow> findFollowsByToMember_NicknameAndAndDeleteAtFalse(String toMemberNickname);

    // 팔로잉 카운트
    int countByFromMember_NicknameAndAndDeleteAtFalse(String fromMemberNickname);

    // 팔로워 카운트
    int countByToMember_NicknameAndAndDeleteAtFalse(String toMemberNickname);

}

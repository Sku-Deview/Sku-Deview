package kr.co.skudeview.repository;

import kr.co.skudeview.domain.MemberSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberSkillRepository extends JpaRepository<MemberSkill, Long> {

    List<MemberSkill> findMemberSkillByMember_IdAndDeleteAtFalse(Long memberId);

}

package kr.co.skudeview.repository;

import kr.co.skudeview.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByTelephone(String telephone);

    Optional<Member> findMemberByNickname(String nickname);

    boolean existsMemberByEmail(String email);

    boolean existsMemberByTelephone(String telephone);

    boolean existsMemberByNickname(String nickname);

}

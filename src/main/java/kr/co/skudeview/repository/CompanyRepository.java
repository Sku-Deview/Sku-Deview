package kr.co.skudeview.repository;

import kr.co.skudeview.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findCompaniesByMember_IdAndDeleteAtFalse(Long memberId);

    Optional<Company> findCompanyByMember_IdAndIdAndDeleteAtFalse(Long memberId, Long id);

    Optional<Company> findCompanyByIdAndDeleteAtFalse(Long id);

}

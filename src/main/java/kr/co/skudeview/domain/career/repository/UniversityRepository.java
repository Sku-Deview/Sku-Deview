package kr.co.skudeview.domain.career.repository;

import kr.co.skudeview.domain.career.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {
    Optional<University> findByUnivNameAndMajor(String univName, String major);
}

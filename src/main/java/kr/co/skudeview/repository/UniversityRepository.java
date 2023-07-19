package kr.co.skudeview.repository;

import kr.co.skudeview.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University,Long> {
    
}

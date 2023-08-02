package kr.co.skudeview.repository;

import kr.co.skudeview.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}

package com.swamptea.diplom.repo;

import com.swamptea.diplom.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
    Token findByT(String t);
}

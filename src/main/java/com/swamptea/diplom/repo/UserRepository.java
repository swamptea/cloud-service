package com.swamptea.diplom.repo;

import com.swamptea.diplom.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByLogin(String username);
}

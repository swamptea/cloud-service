package com.swamptea.diplom.repo;

import com.swamptea.diplom.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String username);
}

package com.mc.demo.ergon.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.mc.demo.ergon.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    long countByUsername(String username);
    Optional<User> findByUsername(String username);
}
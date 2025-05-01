package com.artglimpse.repository.auth;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.artglimpse.model.auth.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}

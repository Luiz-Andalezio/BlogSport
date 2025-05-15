package com.spring.blogsport.repository;

import com.spring.blogsport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Finds a user by email
    Optional<User> findByEmail(String email);
}

package com.spring.blogsport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.blogsport.model.Category;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Optional: find category by name
    Optional<Category> findByName(String name);
}

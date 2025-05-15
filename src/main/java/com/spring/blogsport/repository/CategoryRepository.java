package com.spring.blogsport.repository;

import com.spring.blogsport.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Optional: find category by name
    Category findByName(String name);
}

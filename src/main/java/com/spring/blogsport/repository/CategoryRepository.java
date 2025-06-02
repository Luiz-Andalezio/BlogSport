package com.spring.blogsport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.blogsport.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Optional: find category by name
    Category findByName(String name);

    // Returns category name (case insensitive)
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY c.name ASC")
    List<Category> searchByKeyword(@Param("keyword") String keyword);

    // Returns top3 by name (case insensitive)
    List<Category> findTop3ByNameContainingIgnoreCaseOrderByNameAsc(String name);
}
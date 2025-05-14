package com.spring.blogsport.repository;

import com.spring.blogsport.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Returns all posts by user ID
    List<Post> findByUserId(Long userId);

    // Returns all posts by category ID
    List<Post> findByCategoryId(Long categoryId);
}

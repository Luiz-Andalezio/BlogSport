package com.spring.blogsport.repository;

import com.spring.blogsport.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Returns all comments for a specific post
    List<Comment> findByPostId(Long postId);

    // Returns all comments by a specific user
    List<Comment> findByUserId(Long userId);

    long countByPostId(Long postId);
}

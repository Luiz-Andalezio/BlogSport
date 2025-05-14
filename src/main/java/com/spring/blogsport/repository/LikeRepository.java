package com.spring.blogsport.repository;

import com.spring.blogsport.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // Returns a like by post ID and user ID
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);

    // Counts likes for a post
    long countByPostId(Long postId);
}

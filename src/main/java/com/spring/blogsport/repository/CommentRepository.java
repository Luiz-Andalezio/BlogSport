package com.spring.blogsport.repository;

import com.spring.blogsport.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Returns all comments for a specific post
    List<Comment> findByPostId(Long postId);

    // Returns all comments by a specific user
    List<Comment> findByUserId(Long userId);

    long countByPostId(Long postId);

    // Buscar apenas comentários principais (não replies) com fetch das replies
    @Query("SELECT c FROM Comment c LEFT JOIN FETCH c.replies WHERE c.post.id = :postId AND c.parent IS NULL ORDER BY c.createdAt ASC")
    List<Comment> findByPostIdAndParentIsNull(@Param("postId") Long postId);

    // Buscar replies de um comentário específico
    @Query("SELECT c FROM Comment c WHERE c.parent.id = :parentId ORDER BY c.createdAt ASC")
    List<Comment> findByParentId(@Param("parentId") Long parentId);

    // Contar total de comentários incluindo replies
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    int countAllByPostId(@Param("postId") Long postId);
}

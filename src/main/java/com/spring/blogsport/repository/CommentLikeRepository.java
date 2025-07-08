package com.spring.blogsport.repository;

import com.spring.blogsport.model.CommentLike;
import com.spring.blogsport.model.Comment;
import com.spring.blogsport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    
    // Verifica se o usuário já curtiu o comentário
    boolean existsByCommentAndUser(Comment comment, User user);
    
    // Remove like específico
    void deleteByCommentAndUser(Comment comment, User user);
    
    // Busca like específico
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
    
    // Conta likes por comentário
    int countByCommentId(Long commentId);
    
    // Busca por comentário e usuário usando IDs
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
}

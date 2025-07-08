package com.spring.blogsport.service;

import com.spring.blogsport.model.CommentLike;
import com.spring.blogsport.model.Comment;
import com.spring.blogsport.model.User;
import com.spring.blogsport.repository.CommentLikeRepository;
import com.spring.blogsport.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    public CommentLikeService(CommentLikeRepository commentLikeRepository,
                             CommentRepository commentRepository) {
        this.commentLikeRepository = commentLikeRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public boolean toggleLike(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (commentLikeRepository.existsByCommentAndUser(comment, user)) {
            // Remove like
            commentLikeRepository.deleteByCommentAndUser(comment, user);
            return false; // unliked
        } else {
            // Add like
            CommentLike like = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();
            commentLikeRepository.save(like);
            return true; // liked
        }
    }

    public boolean isLikedByUser(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("Comment not found"));
        return commentLikeRepository.existsByCommentAndUser(comment, user);
    }

    public int getLikeCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }
}
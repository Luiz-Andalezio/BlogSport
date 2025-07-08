package com.spring.blogsport.service;

import com.spring.blogsport.model.Comment;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.model.User;
import com.spring.blogsport.repository.CommentRepository;
import com.spring.blogsport.repository.PostRepository;
import com.spring.blogsport.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Adds a comment to a post
    public Comment addComment(Long postId, Long userId, Comment comment) {
        if (comment.getContent() == null || comment.getContent().isBlank())
            throw new IllegalArgumentException("Comment content is required");
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    // Gets comments for a post
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    } // util para listar todos os comentarios de um post

    // Gets comments by a user
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    } // para listar todos os comentarios de um usuario

    // Updates a comment
    public Comment updateComment(Long commentId, String newContent) {
        if (newContent == null || newContent.isBlank())
            throw new IllegalArgumentException("Comment content is required");
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    // Deletes a comment
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id))
            throw new IllegalArgumentException("Comment not found");
        commentRepository.deleteById(id);
    }

    // Gets a comment by ID
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    } // para acessaar um comentario especifico

    // contador de comentarios de um post
    public long countComments(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    // Buscar apenas comentários principais (sem replies)
    public List<Comment> getMainCommentsByPostId(Long postId) {
        List<Comment> mainComments = commentRepository.findByPostIdAndParentIsNull(postId);
        System.out.println("DEBUG: Found " + mainComments.size() + " main comments for post " + postId);
        return mainComments;
    }

    // Buscar replies de um comentário
    public List<Comment> getRepliesByParentId(Long parentId) {
        List<Comment> replies = commentRepository.findByParentId(parentId);
        System.out.println("DEBUG: Found " + replies.size() + " replies for comment " + parentId);
        return replies;
    }

    // Criar reply
    public Comment addReply(Long parentCommentId, Long userId, String content) {
        Comment parentComment = getCommentById(parentCommentId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment reply = Comment.builder()
                .content(content)
                .post(parentComment.getPost()) // Mesmo post do comentário pai
                .user(user)
                .parent(parentComment) // Define como reply
                .build();

        Comment savedReply = commentRepository.save(reply);
        System.out.println("DEBUG: Created reply with ID: " + savedReply.getId() + " for comment: " + parentCommentId);
        return savedReply;
    }

    // Contar total de comentários incluindo replies
    public int getTotalCommentCount(Long postId) {
        return commentRepository.countAllByPostId(postId);
    }

}

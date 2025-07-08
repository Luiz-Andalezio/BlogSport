package com.spring.blogsport.controller;

import com.spring.blogsport.model.Comment;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.model.User;
import com.spring.blogsport.model.Category;
import com.spring.blogsport.service.CategoryService;
import com.spring.blogsport.service.CommentLikeService;
import com.spring.blogsport.service.CommentService;
import com.spring.blogsport.service.PostService;
import com.spring.blogsport.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentLikeService commentLikeService;

    public CommentController(CommentService commentService,
            UserService userService,
            PostService postService,
            CategoryService categoryService,
            CommentLikeService commentLikeService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
        this.categoryService = categoryService;
        this.commentLikeService = commentLikeService;
    }

    // Adicionando um comentário a um post
    @PostMapping("/post/{postId}")
    public String addComment(@PathVariable Long postId,
            @RequestParam String content,
            @RequestParam(required = false) Long parentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByEmail(userDetails.getUsername())
                .orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        Comment comment = Comment.builder()
                .content(content)
                .build();

        if (parentId != null) {
            Comment parent = commentService.getCommentById(parentId);
            comment.setParent(parent);
        }

        commentService.addComment(postId, user.getId(), comment);
        return "redirect:/posts/" + postId;
    }

    // Shows edit form in separate page
    @GetMapping("/{id}/edit")
    public String editCommentForm(@PathVariable Long id, @RequestParam Long postId, Model model) {
        Post post = postService.getPostById(postId);
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        Comment comment = commentService.getCommentById(id);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("editingCommentId", id);
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("comment", comment);

        return "posts/postDetails";
    }

    // Handles edit submission
    @PostMapping("/{id}/edit")
    public String updateComment(@PathVariable Long id,
            @RequestParam String content) {
        Comment updated = commentService.updateComment(id, content);
        return "redirect:/posts/" + updated.getPost().getId();
    }

    // Deletes a comment
    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long id) {
        Long postId = commentService.getCommentById(id).getPost().getId();
        commentService.deleteComment(id);
        return "redirect:/posts/" + postId;
    }

    // Endpoint para curtir/descurtir comentário
    @PostMapping("/{commentId}/like")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            boolean liked = commentLikeService.toggleLike(commentId, user);
            int likeCount = commentLikeService.getLikeCount(commentId);

            Map<String, Object> response = new HashMap<>();
            response.put("liked", liked);
            response.put("count", likeCount);
            return response;

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("liked", false);
            error.put("count", 0);
            return error;
        }
    }

    // Endpoint para criar reply
    @PostMapping("/{parentCommentId}/reply")
    public String addReply(@PathVariable Long parentCommentId,
            @RequestParam String content,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            System.out.println("DEBUG: Adding reply to comment " + parentCommentId + " with content: " + content);
            
            User user = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            commentService.addReply(parentCommentId, user.getId(), content);

            // Redirect para o post
            Comment parentComment = commentService.getCommentById(parentCommentId);
            System.out.println("DEBUG: Redirecting to post " + parentComment.getPost().getId());
            return "redirect:/posts/" + parentComment.getPost().getId();

        } catch (Exception e) {
            System.err.println("ERROR: Failed to add reply: " + e.getMessage());
            return "redirect:/posts";
        }
    }
}

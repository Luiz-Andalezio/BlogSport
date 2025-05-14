package com.spring.blogsport.controller;

import com.spring.blogsport.model.Comment;
import com.spring.blogsport.model.User;
import com.spring.blogsport.service.CommentService;
import com.spring.blogsport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CommentController(CommentService commentService,
                             UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    // Submits a new comment to a post
    @PostMapping("/post/{postId}")
    public String addComment(@PathVariable Long postId,
                             @RequestParam String content,
                             @RequestParam(required = false) Long parentId,
                             @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

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

    // Displays edit form
    @GetMapping("/{id}/edit")
    public String editCommentForm(@PathVariable Long id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "commentForm";
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
}

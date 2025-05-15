package com.spring.blogsport.controller;

import com.spring.blogsport.model.Post;
import com.spring.blogsport.model.User;
import com.spring.blogsport.service.PostService;
import com.spring.blogsport.service.UserService;
import com.spring.blogsport.service.CategoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public PostController(PostService postService,
                          UserService userService,
                          CategoryService categoryService) {
        this.postService = postService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    // Displays all posts
    @GetMapping
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    // Displays form to create a new post
    @GetMapping("/new")
    public String showPostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "postForm";
    }

    // Handles form submission to create a post
    @PostMapping
    public String savePost(@ModelAttribute @Valid Post post,
                           BindingResult result,
                           @RequestParam("categoryId") Long categoryId,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "postForm";
        }

        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        postService.createPost(post, user.getId(), categoryId);
        return "redirect:/posts";
    }

    // Shows a specific post
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "postDetails";
    }

    // Displays form to edit an existing post
    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "postForm";
    }

    // Updates the post
    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute @Valid Post updatedPost,
                             BindingResult result,
                             @RequestParam("categoryId") Long categoryId,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "postForm";
        }

        updatedPost.setUser(userService.findByEmail(userDetails.getUsername()).orElseThrow());
        updatedPost.setCategory(categoryService.getCategoryById(categoryId));
        postService.updatePost(id, updatedPost);

        return "redirect:/posts/" + id;
    }

    // Deletes a post
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}

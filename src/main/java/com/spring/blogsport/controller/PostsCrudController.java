package com.spring.blogsport.controller;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.Comment;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.model.User;
import com.spring.blogsport.repository.LikeRepository;
import com.spring.blogsport.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/postsCrud")
public class PostsCrudController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final UserService userService;

    public PostsCrudController(PostService postService, CategoryService categoryService, UserService userService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public String postsCrud(Model model) {
        List<Post> posts = postService.getAllPosts();
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("posts", posts);
        model.addAttribute("post", new Post());
        return "posts/postsCrud";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute @Valid Post post,
                             BindingResult result,
                             @RequestParam("categoryId") Long categoryId,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("sidebarCategories", categoryService.getAllCategoriesWithRecentPosts());
            return "posts/postsCrud";
        }
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        postService.createPost(post, user.getId(), categoryId);
        return "redirect:/postsCrud";
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/postsCrud";
    }

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("sidebarCategories", categoryService.getAllCategoriesWithRecentPosts());
        return "posts/editPostCrud";
    }

    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable Long id,
                             @ModelAttribute @Valid Post updatedPost,
                             BindingResult result,
                             @RequestParam("categoryId") Long categoryId,
                             @AuthenticationPrincipal UserDetails userDetails,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("sidebarCategories", categoryService.getAllCategoriesWithRecentPosts());
            return "posts/editPostCrud";
        }
        updatedPost.setUser(userService.findByEmail(userDetails.getUsername()).orElseThrow());
        updatedPost.setCategory(categoryService.getCategoryById(categoryId));
        postService.updatePost(id, updatedPost);
        return "redirect:/postsCrud";
    }
}

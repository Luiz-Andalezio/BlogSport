package com.spring.blogsport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.service.CategoryService;
import com.spring.blogsport.service.PostService;

@Controller
public class PublicCategoryController {

    private final CategoryService categoryService;
    private final PostService postService;

    @Autowired
    public PublicCategoryController(CategoryService categoryService, PostService postService) {
        this.categoryService = categoryService;
        this.postService = postService;
    }

    // htmx endpoint for sidebar categories
    @GetMapping("/categories/sidebar")
    public String sidebarCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesWithRecentPosts());
        return "fragments/categoriesList :: categoriesList";
    }

    @GetMapping("/categories/{id}/posts/sidebar")
    public String sidebarCategoryPosts(@PathVariable Long id, Model model) {
        model.addAttribute("posts", postService.getRecentPostsByCategory(id));
        return "fragments/sidebarPostList :: sidebarPostsList";
    }

    @GetMapping("/categories/{id}")
    public String categoryPosts(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        List<Post> posts = postService.getPostsByCategoryId(id);
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("category", category);
        model.addAttribute("posts", posts);
        sidebarCategories(model);
        return "categoryPostsList";
    }
}
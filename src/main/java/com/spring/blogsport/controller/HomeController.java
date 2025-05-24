package com.spring.blogsport.controller;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.service.CategoryService;
import com.spring.blogsport.service.PostService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PostService postService;
    @Autowired
    private CategoryService categoryService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("categories", categoryService.getAllCategoriesWithRecentPosts());
        model.addAttribute("pageTitle", "Home - BlogSport");
        model.addAttribute("posts", postService.getAllPosts());
        return "index";
    }
}
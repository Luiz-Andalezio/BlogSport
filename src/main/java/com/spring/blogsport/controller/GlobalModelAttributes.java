package com.spring.blogsport.controller;

import com.spring.blogsport.service.CategoryService;
import com.spring.blogsport.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PostService postService;

    @ModelAttribute("sidebarCategories")
    public Object sidebarCategories() {
        return categoryService.getAllCategoriesWithRecentPosts();
    }

    @ModelAttribute("posts")
    public Object posts() {
        return postService.getAllPosts();
    }
}
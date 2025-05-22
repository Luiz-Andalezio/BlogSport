package com.spring.blogsport.controller;

import com.spring.blogsport.controller.PublicCategoryController;
import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.service.CategoryService;
import com.spring.blogsport.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PublicCategoryController publicCategoryController;

    @GetMapping("/search/preview")
    public String searchPreview(@RequestParam("q") String query, Model model) {
        List<Post> posts = postService.searchPosts(query, 3);
        List<Category> categories = categoryService.searchCategories(query, 3);
        model.addAttribute("posts", posts);
        model.addAttribute("categories", categories);
        model.addAttribute("query", query);
        return "fragments/searchPreview :: preview";
    }

    @GetMapping("/search")
    public String searchFull(@RequestParam("q") String query, Model model) {
        List<Post> posts = postService.searchPosts(query, null);
        List<Category> categories = categoryService.searchCategories(query, null);
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();

        model.addAttribute("posts", posts);
        model.addAttribute("categories", categories);
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("query", query);

        return "searchResults";
    }
}
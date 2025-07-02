package com.spring.blogsport.controller;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.service.CategoryService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.blogsport.model.Post;

@Controller
@RequestMapping("/categories")
@PreAuthorize("hasAuthority('ADMIN')") // Only admin users can access
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // List all categories
    @GetMapping
    public String listCategories(Model model) {
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        return "posts/fragments/sidebarCategoriesList";
    }

    // Form for new category
    @GetMapping("/new")
    public String showForm(Model model) {
        List<Category> categories = categoryService.getAllCategoriesWithRecentPosts();
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("pageTitle", "Criar Nova Postagem - BlogSport");
        return "/posts/categoryForm";
    }

    // Create category
    @PostMapping
    public String createCategory(@ModelAttribute @Valid Category category,
            BindingResult result) {
        if (result.hasErrors()) {
            return "categoryForm";
        }
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/save")
    public String saveCategory(@RequestParam String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        categoryService.createCategory(category);
        return "redirect:/categories/categoriesCrud";
    }

    // Edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("category", category);
        return "posts/editCategories";
    }

    // Update category
    @PostMapping("/{id}/edit")
    public String updateCategory(@PathVariable Long id,
            @ModelAttribute @Valid Category category) {
        categoryService.updateCategory(id, category);
        return "redirect:/categories/categoriesCrud";
    }

    // Delete category
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories/categoriesCrud";
    }

    // Manage categories page (list all for admin)
    @GetMapping("/categoriesCrud")
    public String CategoriesCrud(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("categories", categories);
        model.addAttribute("category", new Category());
        return "posts/categoriesCrud";
    }
}

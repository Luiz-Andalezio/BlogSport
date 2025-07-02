package com.spring.blogsport.controller;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.User;
import com.spring.blogsport.service.CommentService;
import com.spring.blogsport.service.UserService;
import com.spring.blogsport.service.CategoryService;
import com.spring.blogsport.service.PostService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/account")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final PostService postService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
            CommentService commentService,
            CategoryService categoryService,
            PostService postService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.postService = postService;
        this.passwordEncoder = passwordEncoder;
    }

    // Show account overview
    @GetMapping
    public String viewAccount(@AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        // posts and categories for sidebar
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("categories", categoryService.getAllCategoriesWithRecentPosts());
        model.addAttribute("pageTitle", "Home - BlogSport");
        model.addAttribute("posts", postService.getAllPosts());

        // user details
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("comments", commentService.getCommentsByUserId(user.getId()));
        return "user/account";
    }

    // Show edit form
    @GetMapping("/edit")
    public String showEditForm(@AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        // posts and categories for sidebar
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("categories", categoryService.getAllCategoriesWithRecentPosts());
        model.addAttribute("pageTitle", "Home - BlogSport");
        model.addAttribute("posts", postService.getAllPosts());

        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "user/accountEdit";
    }

    // Handle update
    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("user") @Valid User updatedUser,
            BindingResult result,
            @RequestParam("currentPassword") String currentPassword,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();

        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            model.addAttribute("error", "Senha atual incorreta.");
            model.addAttribute("user", currentUser);
            return "user/accountEdit";
        }

        if (updatedUser.getName() == null || updatedUser.getName().trim().length() < 2) {
            model.addAttribute("error", "O nome deve ter pelo menos 2 caracteres.");
            model.addAttribute("user", currentUser);
            return "user/accountEdit";
        }

        if (updatedUser.getEmail() == null || updatedUser.getEmail().trim().isEmpty()) {
            model.addAttribute("error", "O email é obrigatório.");
            model.addAttribute("user", currentUser);
            return "user/accountEdit";
        }

        String oldEmail = currentUser.getEmail();

        updatedUser.setId(currentUser.getId());
        updatedUser.setRole(currentUser.getRole());
        updatedUser.setPassword(currentUser.getPassword());
        userService.updateUser(currentUser.getId(), updatedUser);

        if (updatedUser.getEmail() != null && oldEmail != null &&
                !updatedUser.getEmail().equalsIgnoreCase(oldEmail)) {
            return "redirect:/logout";
        }
        return "redirect:/account";
    }

    // Handle image upload (optional enhancement)
    @PostMapping("/upload")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();

        // TODO: Save the file and set the path in user.setProfileImage(...)
        // Exemple: String path = fileStorageService.save(file);
        // user.setProfileImage(path);

        userService.updateUser(user.getId(), user);
        return "redirect:/account";
    }

    @GetMapping("/edit-password")
    public String showEditPassword(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // posts and categories for sidebar
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("categories", categoryService.getAllCategoriesWithRecentPosts());
        model.addAttribute("pageTitle", "Home - BlogSport");
        model.addAttribute("posts", postService.getAllPosts());

        // user details
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("comments", commentService.getCommentsByUserId(user.getId()));
        return "user/accountEditPassword";
    }

    @PostMapping("/edit-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();

        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            model.addAttribute("error", "Senha atual incorreta.");
            model.addAttribute("user", currentUser);
            return "user/accountEditPassword";
        }
        if (newPassword == null || newPassword.length() < 6) {
            model.addAttribute("error", "A nova senha deve ter pelo menos 6 caracteres.");
            model.addAttribute("user", currentUser);
            return "user/accountEditPassword";
        }
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "As senhas não coincidem.");
            model.addAttribute("user", currentUser);
            return "user/accountEditPassword";
        }

        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(currentUser.getId(), currentUser);

        return "redirect:/logout";
    }
}

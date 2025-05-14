package com.spring.blogsport.controller;

import com.spring.blogsport.model.User;
import com.spring.blogsport.service.CommentService;
import com.spring.blogsport.service.LikeService;
import com.spring.blogsport.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;
    private final CommentService commentService;
    private final LikeService likeService;

    @Autowired
    public AccountController(UserService userService,
                             CommentService commentService,
                             LikeService likeService) {
        this.userService = userService;
        this.commentService = commentService;
        this.likeService = likeService;
    }

    // Show account overview
    @GetMapping
    public String viewAccount(@AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("comments", commentService.getCommentsByUserId(user.getId()));
        // Você pode adicionar método para pegar curtidas se desejar
        return "account";
    }

    // Show edit form
    @GetMapping("/edit")
    public String showEditForm(@AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "accountEdit";
    }

    // Handle update
    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute("user") @Valid User updatedUser,
                                BindingResult result,
                                @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            return "accountEdit";
        }

        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        updatedUser.setId(currentUser.getId());
        updatedUser.setRole(currentUser.getRole()); // preserve role
        userService.updateUser(currentUser.getId(), updatedUser);
        return "redirect:/account";
    }

    // Handle image upload (optional enhancement)
    @PostMapping("/upload")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();

        // TODO: Save the file and set the path in user.setProfileImage(...)
        // Exemplo: String path = fileStorageService.save(file);
        // user.setProfileImage(path);

        userService.updateUser(user.getId(), user);
        return "redirect:/account";
    }
}

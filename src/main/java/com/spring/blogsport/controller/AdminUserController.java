package com.spring.blogsport.controller;

import com.spring.blogsport.model.User;
import com.spring.blogsport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("admins", userService.findByRole("ADMIN"));
        model.addAttribute("users", userService.findByRole("USER"));

        model.addAttribute("currentUser", userService.findByEmail(userDetails.getUsername()).orElseThrow());
        return "admin/usersCrud";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/userForm";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user, @RequestParam String role) {
        userService.createUserWithRole(user, role);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/userForm";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User user, @RequestParam String role) {
        userService.updateUser(id, user, role);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/change-password/{id}")
    public String showChangePasswordForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/userChangePassword";
    }

    @PostMapping("/change-password/{id}")
    public String changeUserPassword(@PathVariable Long id,
                                    @RequestParam String newPassword,
                                    @RequestParam String confirmPassword,
                                    Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("user", userService.findById(id));
            model.addAttribute("error", "Passwords do not match.");
            return "admin/userChangePassword";
        }
        userService.changePassword(id, newPassword);
        return "redirect:/admin/users";
    }
}
package com.spring.blogsport.controller;

import com.spring.blogsport.model.User;
import com.spring.blogsport.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "mode", defaultValue = "login") String mode,
                            @RequestParam(value = "success", required = false) String success,
                            Model model) {
        model.addAttribute("mode", mode);
        model.addAttribute("success", success);
        return "login";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid User user,
                           BindingResult result,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model) {
        if (result.hasErrors() || !user.getPassword().equals(confirmPassword)) {
            model.addAttribute("mode", "register");
            model.addAttribute("error", "Please check your data. Passwords must match.");
            return "login";
        }
        userService.registerUser(user);
        return "redirect:/login?mode=login&success=registered";
    }
}
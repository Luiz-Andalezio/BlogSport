package com.spring.blogsport.controller;

import com.spring.blogsport.model.User;
import com.spring.blogsport.service.LikeService;
import com.spring.blogsport.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    @Autowired
    public LikeController(LikeService likeService,
                          UserService userService) {
        this.likeService = likeService;
        this.userService = userService;
    }

    // Handles like action
    @PostMapping("/post/{postId}")
    public String likePost(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        likeService.addLike(postId, user.getId());
        return "redirect:/posts/" + postId;
    }

    // Handles unlike action
    @PostMapping("/post/{postId}/unlike")
    public String unlikePost(@PathVariable Long postId,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        likeService.removeLike(postId, user.getId());
        return "redirect:/posts/" + postId;
    }
}

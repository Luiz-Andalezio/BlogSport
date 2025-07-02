package com.spring.blogsport.controller;

import com.spring.blogsport.model.User;
import com.spring.blogsport.service.LikeService;
import com.spring.blogsport.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;

    public LikeController(LikeService likeService,
            UserService userService) {
        this.likeService = likeService;
        this.userService = userService;
    }

    // // Handles like action
    // @PostMapping("/post/{postId}")
    // public String likePost(@PathVariable Long postId,
    // @AuthenticationPrincipal UserDetails userDetails) {
    // User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
    // likeService.addLike(postId, user.getId());
    // return "redirect:/posts/" + postId;
    // }

    // // Handles unlike action
    // @PostMapping("/post/{postId}/unlike")
    // public String unlikePost(@PathVariable Long postId,
    // @AuthenticationPrincipal UserDetails userDetails) {
    // User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
    // likeService.removeLike(postId, user.getId());
    // return "redirect:/posts/" + postId;
    // }

    // aqui era pra ser usando com o ajax mas nao foi necessario
    // @PostMapping("/post/{postId}/toggle")
    // @ResponseBody
    // public Map<String, Object> addAndRemoveLike(@PathVariable Long postId,
    // @AuthenticationPrincipal UserDetails userDetails) {
    // User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
    // likeService.addAndRemoveLike(postId, user.getId());
    // long updatedLikeCount = likeService.countLikes(postId); // Atualiza o número
    // de likes
    // Map<String, Object> response = new HashMap<>();
    // response.put("likeCount", updatedLikeCount);
    // return response;
    // }

    // // Esse método recebe a requisição do postList.html e rediciona para a página
    // inicial
    @GetMapping("/post/{postId}/toggle")
    public String toggleLikeAndRedirect(@PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        likeService.addAndRemoveLike(postId, user.getId());
        return "redirect:/"; // Redireciona para a página inicial
    }
}

package com.spring.blogsport.controller;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.Comment;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.model.User;
import com.spring.blogsport.repository.LikeRepository;
import com.spring.blogsport.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final CommentService commentService;

    private final LikeService likeService;

    private final LikeRepository likeRepository;

    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;

    public PostController(PostService postService,
            UserService userService,
            CategoryService categoryService, LikeRepository likeRepository, LikeService likeService,
            CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.likeRepository = likeRepository;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    // Displays all posts
    @GetMapping
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("pageTitle", "Posts - BlogSport");
        model.addAttribute("contentFragment", "posts :: content");
        return "layout";
    }

    // Displays form to create a new post
    @GetMapping("/new")
    public String showPostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "postForm";
    }

    // Handles form submission to create a post
    @PostMapping
    public String savePost(@ModelAttribute @Valid Post post,
            BindingResult result,
            @RequestParam("categoryId") Long categoryId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "postForm";
        }

        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        postService.createPost(post, user.getId(), categoryId);
        return "redirect:/posts";
    }

    // Shows a specific post
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        List<Comment> comments = commentService.getCommentsByPostId(post.getId()); // Busca os comentários
        post.setCommentCount(commentService.countComments(post.getId())); // Atualiza a contagem de comentários
        post.setLikeCount(likeRepository.countByPostId(post.getId()));
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments); // Adiciona os comentários ao modelo
        return "posts/postDetails";
    }



    // Displays form to edit an existing post
    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "postForm";
    }

    // Updates the post
    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable Long id,
            @ModelAttribute @Valid Post updatedPost,
            BindingResult result,
            @RequestParam("categoryId") Long categoryId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "postForm";
        }

        updatedPost.setUser(userService.findByEmail(userDetails.getUsername()).orElseThrow());
        updatedPost.setCategory(categoryService.getCategoryById(categoryId));
        postService.updatePost(id, updatedPost);

        return "redirect:/posts/" + id;
    }

    // Deletes a post
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }

    @PostMapping("/{id}/like")
    public String addLike(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        likeService.addLike(id, user.getId());

        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/comment")
    public String addCommentCount(@PathVariable Long id, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.getPostById(id);

        // User user = userService.findByEmail(userDetails.getUsername())
        // .orElseThrow(() -> new RuntimeException("User not found"));
        post.setCommentCount(commentService.countComments(post.getId())); // Atualiza a contagem de comentários
        List<Comment> comments = commentService.getCommentsByPostId(post.getId()); // Busca os comentários
        commentService.countComments(id);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}")
    @ResponseBody
    public Map<String, Object> addComment(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String content) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Cria o objeto Comment
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(postService.getPostById(id)); // Associa o comentário ao post
        comment.setUser(user); // Associa o comentário ao usuário

        // Chama o serviço para adicionar o comentário
        commentService.addComment(id, user.getId(), comment);

        // Retorna uma resposta JSON
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Comment added successfully");
        response.put("commentCount", commentService.countComments(id));
        return response;
    }
}

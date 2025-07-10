package com.spring.blogsport.controller;

import com.spring.blogsport.model.Category;
import com.spring.blogsport.model.Comment;
import com.spring.blogsport.model.Post;
import com.spring.blogsport.model.User;
import com.spring.blogsport.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.Authentication;
@Controller
@RequestMapping("/posts")
public class PostController {

    private final CommentService commentService;

    private final LikeService likeService;

    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;

    public PostController(PostService postService,
            UserService userService,
            CategoryService categoryService, LikeService likeService,
            CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    // Displays all posts
    @GetMapping
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("pageTitle", "Posts - BlogSport");
        model.addAttribute("sidebarCategories", sidebarCategories);
        return "home/index";
    }

    // Displays form to create a new post
    @GetMapping("/new")
    public String showPostForm(Model model) {
        List<Category> categories = categoryService.getAllCategoriesWithRecentPosts();
        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("pageTitle", "Criar Nova Postagem - BlogSport");
        return "posts/createPost";
    }

    // Handles form submission to create a post
    @PostMapping("/create")
    public String savePost(@ModelAttribute @Valid Post post,
            BindingResult result,
            @RequestParam("categoryId") Long categoryId,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("pageTitle", "Criar Nova Postagem - BlogSport");
            return "posts/createPost";
        }

        // Sanitizar o conteúdo HTML
        String content = post.getContent();
        if (content != null) {
            // Remove todas as tags HTML, deixando apenas o texto
            content = content.replaceAll("<[^>]*>", "");
            post.setContent(content);
        }

        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        postService.createPost(post, user.getId(), categoryId);
        return "redirect:/posts";
    }

    // Shows a specific post
@GetMapping("/{id}")
public String getPostDetails(@PathVariable Long id, 
                       @RequestParam(required = false) Long editCommentId,
                       Model model, 
                       Authentication authentication) {
    Post post = postService.getPostById(id);
    
    // Buscar apenas comentários principais
    List<Comment> mainComments = commentService.getMainCommentsByPostId(id);
    
    // IMPORTANTE: Para cada comentário principal, carregar suas replies manualmente
    for (Comment comment : mainComments) {
        loadRepliesRecursively(comment);
        
        // Debug: imprimir para verificar
        System.out.println("DEBUG: Comment ID: " + comment.getId() + " has " + 
                          (comment.getReplies() != null ? comment.getReplies().size() : 0) + " replies");
    }
    
    model.addAttribute("post", post);
    model.addAttribute("comments", mainComments);
    model.addAttribute("editingCommentId", editCommentId);
    
    // IMPORTANTE: Adicionar usuário atual para likes funcionarem
    if (authentication != null) {
        User currentUser = userService.findByEmail(authentication.getName())
            .orElse(null);
        model.addAttribute("currentUser", currentUser);
    }
    
    return "posts/postDetails";
}

    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Post post = postService.getPostById(id);
        
        // Verifica se o usuário atual é o autor do post ou um admin
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        if (!post.getUser().getId().equals(currentUser.getId()) && 
            !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/posts/" + id;
        }

        List<Category> sidebarCategories = categoryService.getAllCategoriesWithRecentPosts();
        model.addAttribute("sidebarCategories", sidebarCategories);
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("pageTitle", "Editar: " + post.getTitle() + " - BlogSport");
        return "posts/editPost";
    }


    @PostMapping("/{id}/delete")
    public String postMethodName(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
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
            model.addAttribute("pageTitle", "Editar Postagem - BlogSport");
            return "posts/createPost";
        }

        updatedPost.setUser(userService.findByEmail(userDetails.getUsername()).orElseThrow());
        updatedPost.setCategory(categoryService.getCategoryById(categoryId));
        postService.updatePost(id, updatedPost);

        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/like")
    public String addLike(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        likeService.addLike(id, user.getId());

        return "redirect:/posts/" + id;
    }

    /**
     * Método recursivo para carregar todas as replies de um comentário
     * e suas sub-replies em todos os níveis (permitindo responder a qualquer comentário)
     */
    private void loadRepliesRecursively(Comment comment) {
        // Carregar replies diretas deste comentário
        List<Comment> replies = commentService.getRepliesByParentId(comment.getId());
        comment.setReplies(replies);
        
        // Para cada reply, carregar recursivamente suas próprias replies
        // Isso permite responder a qualquer comentário, criando threads aninhados
        for (Comment reply : replies) {
            loadRepliesRecursively(reply);
        }
        
        System.out.println("DEBUG: Loaded " + replies.size() + " replies for comment " + comment.getId());
    }
}

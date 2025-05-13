package com.spring.blogsport.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.blogsport.model.Post;
import com.spring.blogsport.service.BlogsportService;

import jakarta.validation.Valid;


@Controller
public class BlogsportController {

    @Autowired
    BlogsportService blogsportService;

    @RequestMapping(value = "/posts", method=RequestMethod.GET)    
    public ModelAndView getPosts() {
        ModelAndView mv = new ModelAndView("posts");
        List<Post> posts = blogsportService.findAll();
        mv.addObject("posts", posts);
        return mv;
    }

    @RequestMapping(value = "/posts/{id}", method=RequestMethod.GET)    
    public ModelAndView getPostDetails(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("postDetails");
        Post post = blogsportService.findById(id);
        mv.addObject("post", post);
        return mv;
    }

    @RequestMapping(value = "/newpost", method=RequestMethod.GET)
    public String getPostForm() {
        return "postForm";
    }

    @RequestMapping(value = "/newpost", method=RequestMethod.POST)
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("error", "Please fill in all fields.");
            return "redirect:/newpost";
        }
        post.setDate(LocalDate.now());
        blogsportService.save(post);
        attributes.addFlashAttribute("success", "Post created successfully!");
        return "redirect:/posts";
    }    
}

package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final CommentService commentService;

    public PostPage(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String post(@PathVariable String id, Model model) {
        Post post;
        try {
            long longId = Long.parseLong(id);
            post = postService.findById(longId);
            model.addAttribute("post", post);
            model.addAttribute("comments", commentService.findAllByPost(post));
            model.addAttribute("commentForm", new CommentForm());
        } catch (NumberFormatException ignored) {}
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String addComment(@PathVariable String id,
                             @Valid @ModelAttribute("commentForm") CommentForm commentForm,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model) {
        Post post = postService.findById(Long.parseLong(id));
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            model.addAttribute("commentForm", commentForm);
            return "PostPage";
        }
        Comment comment = new Comment();
        comment.setText(commentForm.getText());
        postService.addComment(comment, getUser(session), post);
        return "redirect:/post/" + post.getId();
    }
}

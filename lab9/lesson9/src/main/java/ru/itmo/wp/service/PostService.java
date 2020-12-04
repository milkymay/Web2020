package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.repository.CommentRepository;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.TagRepository;
import ru.itmo.wp.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository, TagRepository tagRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(long longId) { return postRepository.findById(longId).orElse(null); }

    public void addComment(Comment comment, User user, Post post) {
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    public void writePost(User user, PostForm postForm) {
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());
        SortedSet<Tag> tags = new TreeSet<Tag>();
        Arrays.stream(postForm.getTags().split("\\s+")).forEach(el -> {
            Tag tag = tagRepository.findByName(el);
            if (tag == null) {
                tag = new Tag();
                tag.setName(el);
                try {
                    tag = tagRepository.save(tag);
                } catch (Exception e) {
                    tag = tagRepository.findByName(el);
                }
            }
            tags.add(tag);
        });
        post.setTags(tags);
        user.addPost(post);
        userRepository.save(user);
    }
}

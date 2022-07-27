package com.example.xyz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.xyz.entity.Post;
import com.example.xyz.repository.PostRepository;

@RestController
@RequestMapping("/api")
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);

    }

    @PutMapping("/posts")
    public Post updatePost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping("/posts")
    public List<Post> getAllPostes() {
        return postRepository.findAll();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPost(@PathVariable Long id) {
        return postRepository.findById(id);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    @GetMapping("/posts/{userId}")
    public List<Post> getPostByAuthor(@PathVariable Long userId) {
        return postRepository.findByAuthor_id(userId);
    }

    @GetMapping("/posts/{keyword}")
    public List<Post> getPost(@PathVariable String keyword, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByKeyword(pageable, keyword);
    }

    @GetMapping("/posts/feeds")
    public List<Post> getFeeds(@PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findFeed(pageable);
    }
}

package com.example.xyz.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xyz.entity.Post;
import com.example.xyz.entity.User;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repository.PostRepository;

@RestController
@RequestMapping("/api")
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Optional<User> oAuthor = userRepository.findByUsername(userDetails.getUsername());
    	if(oAuthor.isPresent()) {
    		User author =oAuthor.get();
    		post.setAuthor(author);
    		post.setNbrOfViews(0);
    		post.setNbrOfStars(0);
    		return postRepository.save(post);
    	}
    	return new Post();
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
    public Post getPost(@PathVariable Long id) {
    	Optional<Post> oPost = postRepository.findById(id);
    	if(oPost.isPresent()) {
    		Post post = oPost.get();
    		int nbViews = post.getNbrOfViews() +1 ;
    		post.setNbrOfViews(nbViews);
    		return postRepository.save(post);
    	}
    	return null;
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    @GetMapping("/posts/author/{userId}")
    public List<Post> getPostByAuthor(@PathVariable Long userId) {
        return postRepository.findByAuthor_userId(userId);
    }

    @PostMapping("/posts/search")
    public List<Post> search(@RequestParam String keyword, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.search(pageable, keyword);
    }

    @GetMapping("/posts/feeds")
    public List<Post> getFeeds() {
    	List list = postRepository.findAll();
    	Comparator<Post> combinedComparator = Comparator.comparing(Post::getNbrOfViews).thenComparing(Post::getNbrOfStars);
        Collections.sort(list,combinedComparator.reversed());
        return list;
    }
}

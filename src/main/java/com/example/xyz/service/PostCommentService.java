package com.example.xyz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.xyz.entity.Post;
import com.example.xyz.entity.PostComment;
import com.example.xyz.entity.User;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repository.PostCommentRepository;

@RestController
@RequestMapping("/api")
public class PostCommentService {
	@Autowired
	private PostCommentRepository postCommentRepository;
	
	@Autowired
    private UserRepository userRepository;

	@PostMapping("/postcomments")
    public PostComment createPost(@RequestBody PostComment postComment) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Optional<User> oAuthor = userRepository.findByUsername(userDetails.getUsername());
    	if(oAuthor.isPresent()) {
    		User author =oAuthor.get();
    		postComment.setAuthor(author);
    		return postCommentRepository.save(postComment);
    	}
    	return new PostComment();
    }

    @PutMapping("/postcomments")
    public PostComment updatePost(@RequestBody PostComment post) {
        return postCommentRepository.save(post);
    }

    @GetMapping("/postcomments")
    public List<PostComment> getAllPostes() {
        return postCommentRepository.findAll();
    }

    @GetMapping("/postcomments/{id}")
    public Optional<PostComment> getPost(@PathVariable Long id) {
        return postCommentRepository.findById(id);
    }

    @DeleteMapping("/postcomments/{id}")
    public void deletePost(@PathVariable Long id) {
        postCommentRepository.deleteById(id);
    }
    /*
     * @GetMapping("/postcomments/getbysector/{id}")
     * public List<Post> getPostBySector(@PathVariable Long id) {
     * log.debug("REST request to get Post : {}", id);
     * List<Post> post = postCommentRepository.findBySubsector_Sector_id(id);
     * return post;
     * }
     */
}

package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public PostComment getPostComment() {
		return postComment;
	}

	public void setPostComment(PostComment postComment) {
		this.postComment = postComment;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@OneToOne
    @JoinColumn(name="postComment_id", nullable=false)
    private PostComment postComment;

    @ManyToOne()
    private User author;
}

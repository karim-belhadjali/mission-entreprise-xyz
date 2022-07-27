package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private int nbrOfStars;
    private int nbrOfViews;

    @OneToMany(mappedBy="post")
    private List<PostComment> comments;

    @ManyToOne()
    private User author;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNbrOfStars() {
		return nbrOfStars;
	}

	public void setNbrOfStars(int nbrOfStars) {
		this.nbrOfStars = nbrOfStars;
	}

	public int getNbrOfViews() {
		return nbrOfViews;
	}

	public void setNbrOfViews(int nbrOfViews) {
		this.nbrOfViews = nbrOfViews;
	}

	public List<PostComment> getComments() {
		return comments;
	}

	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

}

package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class EventFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer rating;

	@OneToMany
	@JoinColumn(name="event_feedback")
	private List<Comment> comments;

	@ManyToOne
	@JoinColumn(name="submitted_by")
	private User submittedBy;
}

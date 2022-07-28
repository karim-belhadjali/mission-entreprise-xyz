package com.example.xyz.entity.event_mgmt;

import com.example.xyz.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ActivityFeedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer rating;
	private String comment;
	private Boolean isFavorite;

	@OneToOne
	@JoinColumn(name = "submitted_by_id")
	private User submittedBy;

	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
}

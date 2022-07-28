package com.example.xyz.entity.event_mgmt;

import com.example.xyz.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private String comment;

	@OneToOne
	@JoinColumn(name = "submitted_by_id")
	private User submittedBy;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
}

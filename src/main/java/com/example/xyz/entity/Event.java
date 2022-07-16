package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String description;
	private Date startTimestamp;
	private Date endTimestamp;

	@OneToMany
	@JoinColumn(name="event")
	private List<EventInvite> eventInvites;

	@OneToMany
	@JoinColumn(name="event")
	private List<EventFeedback> eventFeedbacks;

	@OneToMany
	@JoinColumn(name="event")
	private List<Activity> activities;

	@ManyToOne
	@JoinColumn(name="created_by")
	private User createdBy;
}

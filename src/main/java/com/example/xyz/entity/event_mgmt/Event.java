package com.example.xyz.entity.event_mgmt;

import com.example.xyz.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String description;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date startTimestamp;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private Date endTimestamp;

	@JsonIgnore
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
	private List<EventInvite> eventInvites;

	@JsonIgnore
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
	private List<EventFeedback> eventFeedbacks;

	@JsonIgnore
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
	private List<Activity> activities;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "created_by_id")
	private User createdBy;
}

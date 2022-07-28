package com.example.xyz.entity.event_mgmt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;

	@JsonIgnore
	@OneToMany(mappedBy = "activity", cascade = CascadeType.REMOVE)
	private List<ActivityFeedback> activityFeedbacks;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
}

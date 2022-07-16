package com.example.xyz.entity;

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

	@OneToMany
	@JoinColumn(name="activity")
  private List<ActivityFeedback> activityFeedbacks;
}

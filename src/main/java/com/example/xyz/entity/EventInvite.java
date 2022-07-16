package com.example.xyz.entity;

import com.example.xyz.enums.EventInviteStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class EventInvite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String emailSubject;
	private String emailBody;

	@Enumerated(EnumType.STRING)
	private EventInviteStatus status;

	private Date timestamp;

	@ManyToOne
	@JoinColumn(name="sent_by")
	private User sentBy;

	@ManyToMany
	private List<User> receivedBy;

}

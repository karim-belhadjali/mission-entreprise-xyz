package com.example.xyz.entity.event_mgmt;

import com.example.xyz.entity.User;
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
	@JoinColumn(name = "sent_by_id")
	private User sentBy;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "event_invites_receivers",
			joinColumns = @JoinColumn(name = "event_invite_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<User> receivedBy;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
}

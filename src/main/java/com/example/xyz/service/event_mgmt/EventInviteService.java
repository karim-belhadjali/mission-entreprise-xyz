package com.example.xyz.service.event_mgmt;

import com.example.xyz.entity.User;
import com.example.xyz.entity.event_mgmt.Event;
import com.example.xyz.entity.event_mgmt.EventInvite;
import com.example.xyz.enums.EventInviteStatus;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repositories.event_mgmt.EventInviteRepository;
import com.example.xyz.repositories.event_mgmt.EventRepository;
import com.example.xyz.service.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@EnableAutoConfiguration
@Service
public class EventInviteService {

	@Autowired
	private EventInviteRepository eventInviteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public EventInvite sendEventInvite(final EventInvite eventInvite, final Long eventId) {
		String username = ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Username not found"));
		eventInvite.setSentBy(user);

		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new BadRequestException(String.format("Event %s not found", eventId)));
		eventInvite.setEvent(event);

		eventInvite.setStatus(EventInviteStatus.ACTIVE);
		eventInvite.setTimestamp(new Date());

		sendInviteEmails(eventInvite.getReceivedBy(), eventInvite);

		return eventInviteRepository.save(eventInvite);
	}

	private void sendInviteEmails(final List<User> users, final EventInvite eventInvite) {
		users.stream()
				.map(item -> userRepository.findById(2L).get().getMail())
				.forEach(item -> sendInviteEmail(item, eventInvite));
	}

	private void sendInviteEmail(final String userEmail, final EventInvite eventInvite) throws MailException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setFrom(sender);
		mailMessage.setTo(userEmail);
		mailMessage.setText(eventInvite.getEmailBody());
		mailMessage.setSubject(eventInvite.getEmailSubject());

		javaMailSender.send(mailMessage);
	}
}

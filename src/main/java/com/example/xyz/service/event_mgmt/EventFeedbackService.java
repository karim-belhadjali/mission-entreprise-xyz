package com.example.xyz.service.event_mgmt;

import com.example.xyz.entity.User;
import com.example.xyz.entity.event_mgmt.Event;
import com.example.xyz.entity.event_mgmt.EventFeedback;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repositories.event_mgmt.EventFeedbackRepository;
import com.example.xyz.repositories.event_mgmt.EventRepository;
import com.example.xyz.service.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EventFeedbackService {

	@Autowired
	private EventFeedbackRepository eventFeedbackRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	public EventFeedback submitEventFeedback(final EventFeedback eventFeedback, final Long eventId) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new BadRequestException(String.format("Event %s not found", eventId)));
		eventFeedback.setEvent(event);

		String username = ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Username not found"));
		eventFeedback.setSubmittedBy(user);

		return eventFeedbackRepository.save(eventFeedback);
	}

	public void deleteEventFeedback(final Long id) {
		eventFeedbackRepository.deleteById(id);
	}
}

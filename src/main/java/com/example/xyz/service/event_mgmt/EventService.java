package com.example.xyz.service.event_mgmt;

import com.example.xyz.entity.User;
import com.example.xyz.entity.event_mgmt.Event;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repositories.event_mgmt.EventRepository;
import com.example.xyz.service.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Event> getLoggedInUserEvents() {
		String username = ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Username not found"));

		return eventRepository.findByCreatedBy(user);
	}

	public Event createEvent(final Event event) {
		String username = ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Username not found"));

		event.setCreatedBy(user);
		return eventRepository.save(event);
	}

	public Event updateEvent(final Long id, final Event event) {
		Event eventById = eventRepository.findById(id)
				.orElseThrow(() -> new BadRequestException(String.format("Event %s not found", id)));

		eventById.setTitle(event.getTitle());
		eventById.setDescription(event.getDescription());
		eventById.setStartTimestamp(event.getStartTimestamp());
		eventById.setEndTimestamp(event.getEndTimestamp());

		return eventRepository.save(eventById);
	}

	public void deleteEvent(final Long id) {
		eventRepository.deleteById(id);
	}
}

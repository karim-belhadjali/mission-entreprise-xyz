package com.example.xyz.controller.event_mgmt;

import com.example.xyz.entity.event_mgmt.Event;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.service.event_mgmt.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class EventController {

	@Autowired
	private EventService eventService;

	@GetMapping("/events")
	public List<Event> getLoggedInUserEvents() {
		return eventService.getLoggedInUserEvents();
	}

	@PostMapping("/events")
	public ResponseEntity<String> createEvent(@RequestBody final Event event) {
		try {
			Long id = eventService.createEvent(event).getId();
			return new ResponseEntity<>(String.format("Event %s saved", id), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/events/{id}")
	public ResponseEntity<String> updateEvent(
			@PathVariable("id") final Long eventId,
			@RequestBody final Event event
	) {
		try {
			Long id = eventService.updateEvent(eventId, event).getId();
			return new ResponseEntity<>(String.format("Event %s updated", id), HttpStatus.OK);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/events/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable("id") final Long id) {
		try {
			eventService.deleteEvent(id);
			return new ResponseEntity<>(String.format("Event %s deleted", id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

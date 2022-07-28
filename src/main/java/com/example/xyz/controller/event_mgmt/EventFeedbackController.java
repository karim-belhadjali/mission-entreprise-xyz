package com.example.xyz.controller.event_mgmt;

import com.example.xyz.entity.event_mgmt.EventFeedback;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.service.event_mgmt.EventFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class EventFeedbackController {

	@Autowired
	private EventFeedbackService eventFeedbackService;

	@PostMapping("/event_feedbacks")
	public ResponseEntity<String> submitEventFeedback(
			@RequestBody final EventFeedback eventFeedback,
			@RequestParam("event_id") final Long eventId
	) {
		try {
			Long id = eventFeedbackService.submitEventFeedback(eventFeedback, eventId).getId();
			return new ResponseEntity<>(String.format("Event feedback %s saved", id), HttpStatus.CREATED);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/event_feedbacks/{id}")
	public ResponseEntity<String> deleteEventFeedback(@PathVariable("id") final Long id) {
		try {
			eventFeedbackService.deleteEventFeedback(id);
			return new ResponseEntity<>(String.format("Event feedback %s deleted", id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

package com.example.xyz.controller.event_mgmt;

import com.example.xyz.entity.event_mgmt.EventInvite;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.service.event_mgmt.EventInviteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EventInviteController {

	@Autowired
	private EventInviteService eventInviteService;

	@PostMapping("/event_invites")
	public ResponseEntity<String> sendEventInvite(
			@RequestBody final EventInvite eventInvite,
			@RequestParam("event_id") final Long eventId
	) {
		try {
			Long id = eventInviteService.sendEventInvite(eventInvite, eventId).getId();
			return new ResponseEntity<>(String.format("Event invite %s sent", id), HttpStatus.CREATED);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

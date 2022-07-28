package com.example.xyz.controller.event_mgmt;

import com.example.xyz.entity.event_mgmt.Activity;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.service.event_mgmt.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@PostMapping("/activities")
	public ResponseEntity<String> createActivity(
			@RequestBody final Activity activity,
			@RequestParam("event_id") final Long eventId
	) {
		try {
			Long id = activityService.createActivity(activity, eventId).getId();
			return new ResponseEntity<>(String.format("Activity %s saved", id), HttpStatus.CREATED);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/activities/{id}")
	public ResponseEntity<String> updateActivity(
			@PathVariable("id") final Long activityId,
			@RequestBody final Activity activity
	) {
		try {
			Long id = activityService.updateActivity(activityId, activity).getId();
			return new ResponseEntity<>(String.format("Activity %s updated", id), HttpStatus.OK);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/activities/{id}")
	public ResponseEntity<String> deleteActivity(@PathVariable("id") final Long id) {
		try {
			activityService.deleteActivity(id);
			return new ResponseEntity<>(String.format("Activity %s deleted", id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

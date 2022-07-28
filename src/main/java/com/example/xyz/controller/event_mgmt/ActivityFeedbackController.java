package com.example.xyz.controller.event_mgmt;

import com.example.xyz.entity.event_mgmt.ActivityFeedback;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.service.event_mgmt.ActivityFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ActivityFeedbackController {

	@Autowired
	private ActivityFeedbackService ActivityFeedbackService;

	@PostMapping("/activity_feedbacks")
	public ResponseEntity<String> submitActivityFeedback(
			@RequestBody final ActivityFeedback ActivityFeedback,
			@RequestParam("activity_id") final Long activityId
	) {
		try {
			Long id = ActivityFeedbackService.submitActivityFeedback(ActivityFeedback, activityId).getId();
			return new ResponseEntity<>(String.format("Activity feedback %s saved", id), HttpStatus.CREATED);
		} catch (BadRequestException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/activity_feedbacks/{id}")
	public ResponseEntity<String> deleteActivityFeedback(@PathVariable("id") final Long id) {
		try {
			ActivityFeedbackService.deleteActivityFeedback(id);
			return new ResponseEntity<>(String.format("Activity feedback %s deleted", id), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

package com.example.xyz.service.event_mgmt;

import com.example.xyz.entity.User;
import com.example.xyz.entity.event_mgmt.Activity;
import com.example.xyz.entity.event_mgmt.ActivityFeedback;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repositories.event_mgmt.ActivityFeedbackRepository;
import com.example.xyz.repositories.event_mgmt.ActivityRepository;
import com.example.xyz.service.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ActivityFeedbackService {

	@Autowired
	private ActivityFeedbackRepository activityFeedbackRepository;

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private UserRepository userRepository;

	public ActivityFeedback submitActivityFeedback(final ActivityFeedback activityFeedback, final Long activityId) {
		Activity activity = activityRepository.findById(activityId)
				.orElseThrow(() -> new BadRequestException(String.format("Activity %s not found", activityId)));
		activityFeedback.setActivity(activity);

		String username = ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUsername();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Username not found"));
		activityFeedback.setSubmittedBy(user);

		return activityFeedbackRepository.save(activityFeedback);
	}

	public void deleteActivityFeedback(final Long id) {
		activityFeedbackRepository.deleteById(id);
	}
}

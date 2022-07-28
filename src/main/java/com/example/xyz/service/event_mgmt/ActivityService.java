package com.example.xyz.service.event_mgmt;

import com.example.xyz.entity.event_mgmt.Activity;
import com.example.xyz.entity.event_mgmt.Event;
import com.example.xyz.exception.BadRequestException;
import com.example.xyz.repositories.event_mgmt.ActivityRepository;
import com.example.xyz.repositories.event_mgmt.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	@Autowired
	private EventRepository eventRepository;

	public Activity createActivity(final Activity activity, final Long eventId) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new BadRequestException(String.format("Event %s not found", eventId)));

		activity.setEvent(event);
		return activityRepository.save(activity);
	}

	public Activity updateActivity(final Long id, final Activity activity) {
		Activity activityById = activityRepository.findById(id)
				.orElseThrow(() -> new BadRequestException(String.format("Activity %s not found", id)));

		activityById.setName(activity.getName());
		activityById.setDescription(activity.getDescription());

		return activityRepository.save(activityById);
	}

	public void deleteActivity(final Long id) {
		activityRepository.deleteById(id);
	}
}

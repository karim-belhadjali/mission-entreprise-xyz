package com.example.xyz.repositories.event_mgmt;

import com.example.xyz.entity.event_mgmt.EventFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventFeedbackRepository extends JpaRepository<EventFeedback, Long> {
}

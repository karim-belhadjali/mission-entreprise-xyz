package com.example.xyz.repositories.event_mgmt;

import com.example.xyz.entity.event_mgmt.ActivityFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityFeedbackRepository extends JpaRepository<ActivityFeedback, Long> {
}

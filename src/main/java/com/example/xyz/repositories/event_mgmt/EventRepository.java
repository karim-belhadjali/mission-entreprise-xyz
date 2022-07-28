package com.example.xyz.repositories.event_mgmt;

import com.example.xyz.entity.User;
import com.example.xyz.entity.event_mgmt.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByCreatedBy(final User user);
}

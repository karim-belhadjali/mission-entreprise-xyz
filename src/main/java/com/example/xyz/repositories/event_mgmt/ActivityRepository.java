package com.example.xyz.repositories.event_mgmt;

import com.example.xyz.entity.event_mgmt.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}

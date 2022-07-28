package com.example.xyz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.xyz.entity.Notification;
import com.example.xyz.enums.NotificationStatus;
import com.example.xyz.enums.NotificationType;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findByNotificationStatus(NotificationStatus notificationStatus);

    public List<Notification> findByNotificationType(NotificationType notificationType);

}

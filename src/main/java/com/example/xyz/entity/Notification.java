package com.example.xyz.entity;

import lombok.Data;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.example.xyz.enums.NotificationStatus;
import com.example.xyz.enums.NotificationType;

import java.sql.Timestamp;

@Data
@Entity

public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String description;

    @CreationTimestamp
    private Timestamp creationDateTimeStamp;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "creator_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_to_notify")
    private User userToNotify;

}

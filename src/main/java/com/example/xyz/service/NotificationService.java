package com.example.xyz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.xyz.entity.Notification;
import com.example.xyz.entity.User;
import com.example.xyz.enums.NotificationStatus;
import com.example.xyz.enums.NotificationType;
import com.example.xyz.messagerie.service.EmailService;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.repository.NotificationRepository;
import java.util.List;
import java.util.Optional;

@Component
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public EmailService emailService;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public List<Notification> findByNotificationType(NotificationType notificationType) {
        return notificationRepository.findByNotificationType(notificationType);
    }

    public List<Notification> findByNotificationStatus(NotificationStatus notificationStatus) {
        return notificationRepository.findByNotificationStatus(notificationStatus);
    }

    public Notification saveNotification(Notification notification) {

        return notificationRepository.save(notification);

    }

    public Notification addNotification(Notification notification) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Optional<User> oAuthor = userRepository.findByUsername(userDetails.getUsername());
        if (oAuthor.isPresent()) {
            User author = oAuthor.get();
            notification.setUser(author);
        }
        notification.setNotificationStatus(NotificationStatus.CREATED);
        Notification newNotification = saveNotification(notification);
        sendNotification(newNotification);
        return newNotification;
    }

    public Optional<Notification> getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {

            return notification;
        } else {
            throw new ResourceNotFoundException("Notification Id " + notification.get().getId() + " not found");
        }
    }

    public void deleteNotification(Notification notification) {

        notificationRepository.deleteById(notification.getId());

    }

    public void sendNotification(Notification notification) {

        if (notification.getNotificationStatus().equals(NotificationStatus.CREATED)) {
            emailService.sendSimpleEmail(notification.getUserToNotify().getMail(),
                    "New Notification " + notification.getName() + " " + notification.getNotificationType(),
                    "you have a new " + notification.getNotificationType() + " from "
                            + notification.getUser().getFirstName() + notification.getUser().getLastName());
            notification.setNotificationStatus(NotificationStatus.RECEIVED);
            saveNotification(notification);
        } else if (notification.getNotificationStatus().equals(NotificationStatus.RECEIVED)) {
            emailService.sendSimpleEmail(notification.getUserToNotify().getMail(),
                    "the Notification " + notification.getName() + " " + notification.getNotificationType(),
                    "your Notification " + notification.getNotificationType() + " from "
                            + notification.getUser().getFirstName() + notification.getUser().getLastName()
                            + "Is recieved");
            notification.setNotificationStatus(NotificationStatus.SENDED);
            saveNotification(notification);
        }

    }

}

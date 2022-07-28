package com.example.xyz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.xyz.entity.Notification;
import com.example.xyz.enums.NotificationStatus;
import com.example.xyz.enums.NotificationType;
import com.example.xyz.service.NotificationService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotification() {
        List<Notification> listNotification = null;
        listNotification = notificationService.findAll();
        if (listNotification.isEmpty())
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(listNotification, HttpStatus.OK);
    }

    @GetMapping("/notifications/{notification_Id}")
    public ResponseEntity<Optional<Notification>> getNotificationById(@PathVariable long notification_Id) {
        Optional<Notification> listNotification;
        listNotification = notificationService.getNotificationById(notification_Id);

        if (!listNotification.isPresent())
            return ResponseEntity.noContent().build();
        return new ResponseEntity<>(listNotification, HttpStatus.OK);

    }

    @GetMapping("/notificationsbytype/{notificationType}")
    public ResponseEntity<List<Notification>> getNotificationByType(@PathVariable NotificationType notificationType) {
        List<Notification> listNotification = notificationService.findByNotificationType(notificationType);
        if (listNotification.isEmpty())
            return ResponseEntity.noContent().build();
        return new ResponseEntity<>(listNotification, HttpStatus.OK);

    }

    @GetMapping("/notificationsbystatus/{notificationStatus}")
    public ResponseEntity<List<Notification>> getNotificationByStatus(
            @PathVariable NotificationStatus notificationStatus) {
        List<Notification> listNotification = notificationService.findByNotificationStatus(notificationStatus);
        if (listNotification.isEmpty())
            return ResponseEntity.noContent().build();
        return new ResponseEntity<>(listNotification, HttpStatus.OK);

    }

    @PutMapping("/notifications/{notification_Id}")
    public ResponseEntity<Notification> pullNotification(@PathVariable long notification_Id,
            @RequestBody Notification notification) {

        Optional<Notification> notificationData = notificationService.getNotificationById(notification_Id);

        if (notificationData.isPresent()) {
            return new ResponseEntity<>(notificationService.saveNotification(notification), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/addnotification")
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {
        Notification notificationLocal = notificationService.addNotification(notification);

        if (notificationLocal == null)
            return ResponseEntity.noContent().build();
        return new ResponseEntity<>(notificationLocal, HttpStatus.OK);
    }

    @DeleteMapping("/notifications/{notification_Id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable long notification_Id) {

        Optional<Notification> notificationData = notificationService.getNotificationById(notification_Id);

        if (notificationData.isPresent()) {
            notificationService.deleteNotification(notificationData.get());
            return ResponseEntity.accepted().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/updateNotificationStatus/{notification_Id}")
    public ResponseEntity<Notification> pullNotification(@PathVariable long notification_Id) {

        Optional<Notification> notificationData = notificationService.getNotificationById(notification_Id);

        if (notificationData.isPresent()) {
            Notification newNotification = notificationData.get();
            notificationService.sendNotification(newNotification);
            return ResponseEntity.accepted().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}

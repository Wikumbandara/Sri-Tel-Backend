package com.stl.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "notificationTopic", groupId = "notificationGroup")
    public void listen(String message) {
        System.out.println("Received notification: " + message);

        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}

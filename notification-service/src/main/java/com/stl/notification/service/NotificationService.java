package com.stl.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendNotification(NotificationRequest notificationRequest) {
        try {
            // Convert NotificationRequest object to JSON string
            String message = objectMapper.writeValueAsString(notificationRequest);
            // Send the JSON message to the Kafka topic
            kafkaTemplate.send("notificationTopic", message);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception
        }
    }
}

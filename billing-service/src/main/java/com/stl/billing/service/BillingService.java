package com.stl.billing.service;

import com.stl.billing.entity.Billing;
import com.stl.billing.repository.BillingRepository;
import dto.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final RestTemplate restTemplate;

    public BillingService(BillingRepository billingRepository, RestTemplate restTemplate) {
        this.billingRepository = billingRepository;
        this.restTemplate = restTemplate;
    }

    public void generateMonthlyBill(Long userId) {
        // Call external service to get activated services for the user
        String url = "http://localhost:8082/api/provision/user/" + userId;
        List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

        // Extract service details and calculate the total
        List<Long> serviceIds = new ArrayList<>();
        double totalAmount = 0.0;
        StringBuilder descriptionBuilder = new StringBuilder("Services: ");

        if (response != null) {
            for (Map<String, Object> provisionService : response) {
                Map<String, Object> service = (Map<String, Object>) provisionService.get("service");
                Long serviceId = Long.valueOf((Integer) service.get("id"));
                String serviceName = (String) service.get("name");
                String serviceDescription = (String) service.get("description");
                double servicePrice = Double.valueOf((Integer) service.get("price"));

                // Append service to description
                descriptionBuilder.append(serviceName).append(" - ").append(servicePrice).append("\n; ");

                // Add service ID to active services list
                serviceIds.add(serviceId);

                // Add price to total
                totalAmount += servicePrice;
            }
        }

        // Build the billing entity
        Billing billing = new Billing();
        billing.setUserId(userId);
        billing.setDescription(descriptionBuilder.toString());
        billing.setTotal(totalAmount);
        billing.setStatus("PENDING"); // Initially the billing status is PENDING
        billing.setActiveServices(serviceIds);
        billing.setPaymentMode("N/A"); // Payment mode will be set when paid
        billing.setCreatedDate(LocalDate.now());
        billing.setPaymentDate(null); // Payment date will be set when the user pays

        // Save the billing record
        billingRepository.save(billing);

        sendNotification(userId, "Your new bill has been arrived with a total of $" + totalAmount);
    }

    private void sendNotification(Long userId, String message) {
        // Create notification request object
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUserId(userId);
        notificationRequest.setMessage(message);
        notificationRequest.setType("BILLING_NOTIFICATION");

        // Send notification via notification-service
        String notificationServiceUrl = "http://localhost:8084/api/notifications/send";
        try {
            restTemplate.postForObject(notificationServiceUrl, notificationRequest, Void.class);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public List<Long> getAllUserIds() {
        // Get all active users from the user service
        String url = "http://localhost:8081/api/users";
        List<Long> response = restTemplate.getForObject(url, List.class);

        return response != null ? response : new ArrayList<>();
    }

    public List<Billing> getBillsByUserId(Long id) {
        return billingRepository.findByUserId(id);
    }
}

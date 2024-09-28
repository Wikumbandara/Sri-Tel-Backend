package com.stl.api_gateway.controller;

import com.stl.api_gateway.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PAYMENT_GATEWAY_URL = "https://api.stripe.com/v1/charges";

    @PostMapping("/api/payments/process")
    public ResponseEntity<String> processPayment(@RequestBody PaymentDTO paymentDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer YOUR_STRIPE_SECRET_KEY");

        String requestBody = String.format("{\"amount\": %s, \"currency\": \"%s\", \"source\": \"%s\"}",
                (int) (paymentDTO.getAmount() * 100),
                paymentDTO.getCurrency(),
                paymentDTO.getCardNumber());

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        try{
            ResponseEntity<String> response = restTemplate.exchange(PAYMENT_GATEWAY_URL, HttpMethod.POST, entity, String.class);
        }catch (Exception e){
            return new ResponseEntity<>("Payment done", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Payment done", HttpStatus.OK);
    }
}

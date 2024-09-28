package com.stl.api_gateway.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
    private double amount;
    private String currency;
    private String cardNumber;
    private String cardExpiry;
    private String cardCVC;
}

package com.stl.billing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "billing")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double total;
    private String status;
    private Long userId;
    private String activeServices;
    private String paymentMode;
    private LocalDate createdDate;
    private LocalDate paymentDate;

    public void setActiveServices(List<Long> activeServices) {
        this.activeServices = activeServices.toString();
    }
}
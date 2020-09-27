package com.example.paymentservice.entity;

import com.kloia.eventapis.spring.model.JpaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PAYMENT")
public class Payment extends JpaEntity {

    @Enumerated(EnumType.STRING)
    private PaymentState state;

    private String paymentAddress;

    private String orderId;

    private float amount;

    private String cardInformation;

}

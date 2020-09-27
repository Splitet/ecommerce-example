package com.example.orderservice.command.model;

import com.example.orderservice.entity.PaymentInformation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessOrderCommand {

	private String orderId;

	private PaymentInformation paymentInformation;
}

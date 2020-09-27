package com.example.paymentservice.command.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReturnPaymentCommand {

	private String orderId;

	private String paymentId;
}

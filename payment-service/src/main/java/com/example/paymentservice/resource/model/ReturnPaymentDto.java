package com.example.paymentservice.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPaymentDto {

	private String orderId;

	private String paymentId;
}

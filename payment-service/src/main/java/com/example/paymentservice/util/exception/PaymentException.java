package com.example.paymentservice.util.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PaymentException extends Exception{

	protected String message;
}

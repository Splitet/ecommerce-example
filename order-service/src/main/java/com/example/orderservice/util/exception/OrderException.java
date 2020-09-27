package com.example.orderservice.util.exception;

public class OrderException extends Exception {
	public OrderException(String message) {
		super(message);
	}

	public OrderException(String message, Throwable cause) {
		super(message, cause);
	}
}

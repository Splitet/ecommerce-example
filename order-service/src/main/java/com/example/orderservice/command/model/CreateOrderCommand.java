package com.example.orderservice.command.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderCommand {

	private String stockId;

	private int orderAmount;

	private String description;
}

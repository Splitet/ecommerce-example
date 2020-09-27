package com.example.orderservice.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
	private String stockId;
	private int orderAmount;
	private String description;
}

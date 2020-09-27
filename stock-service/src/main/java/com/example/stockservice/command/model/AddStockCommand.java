package com.example.stockservice.command.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddStockCommand {

	private long stockToAdd;

	private String stockId;
}

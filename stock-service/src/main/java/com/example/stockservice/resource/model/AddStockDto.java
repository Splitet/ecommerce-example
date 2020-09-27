package com.example.stockservice.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStockDto {

	private long stockToAdd;

	private String stockId;
}

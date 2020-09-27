package com.example.stockservice.event.spec;

import com.example.stockservice.event.model.published.StockNotEnoughEvent;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.entity.StockState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class StockNotEnoughSpec extends EntityFunctionSpec<Stock, StockNotEnoughEvent> {
	public StockNotEnoughSpec() {
		super((stock, event) -> {
			StockNotEnoughEvent eventData = event.getEventData();
			stock.setState(StockState.OUT);
			return stock;
		});
	}
}

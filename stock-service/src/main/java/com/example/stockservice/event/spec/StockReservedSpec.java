package com.example.stockservice.event.spec;

import com.example.stockservice.event.model.published.StockReservedEvent;
import com.example.stockservice.entity.Stock;
import io.splitet.core.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class StockReservedSpec extends EntityFunctionSpec<Stock, StockReservedEvent> {
	public StockReservedSpec() {
		super((stock, event) -> {
			StockReservedEvent eventData = event.getEventData();
			stock.setRemainingStock(stock.getRemainingStock() - eventData.getNumberOfItemsSold());
			return stock;
		});
	}
}
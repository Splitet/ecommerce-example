package com.example.stockservice.event.spec;

import com.example.stockservice.event.model.published.StockAddedEvent;
import com.example.stockservice.entity.Stock;
import io.splitet.core.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class StockAddedSpec extends EntityFunctionSpec<Stock, StockAddedEvent> {
	public StockAddedSpec() {
		super((stock, event) -> {
			StockAddedEvent eventData = event.getEventData();
			stock.setRemainingStock(stock.getRemainingStock() + eventData.getAddedStock());
			return stock;
		});
	}
}

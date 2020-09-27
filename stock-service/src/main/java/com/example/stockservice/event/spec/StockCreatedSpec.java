package com.example.stockservice.event.spec;

import com.example.stockservice.event.model.published.StockCreatedEvent;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.entity.StockState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class StockCreatedSpec extends EntityFunctionSpec<Stock, StockCreatedEvent> {
	public StockCreatedSpec() {
		super((stock, event) -> {
			StockCreatedEvent eventData = event.getEventData();
			stock.setRemainingStock(0);
			stock.setStockName(eventData.getStockName());
			stock.setState(StockState.INUSE);
			return stock;
		});
	}
}

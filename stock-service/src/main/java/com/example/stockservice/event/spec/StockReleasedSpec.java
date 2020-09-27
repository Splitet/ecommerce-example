package com.example.stockservice.event.spec;

import com.example.stockservice.event.model.published.StockReleasedEvent;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.entity.StockState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class StockReleasedSpec extends EntityFunctionSpec<Stock, StockReleasedEvent> {
	public StockReleasedSpec() {
		super((stock, event) -> {
			StockReleasedEvent eventData = event.getEventData();
			stock.setRemainingStock(stock.getRemainingStock() + eventData.getNumberOfItemsReleased());
			if (stock.getState() == StockState.OUT)
				stock.setState(StockState.INUSE);
			return stock;
		});
	}
}
package com.example.stockservice.event.spec;

import com.example.stockservice.event.model.published.StockAddedEvent;
import io.splitet.core.api.RollbackSpec;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockAddedRollbackSpec implements RollbackSpec<StockAddedEvent> {

	@Override
	public void rollback(StockAddedEvent event) {
		log.warn("Rolling back StockAddedEvent for: " + event.toString());
	}
}

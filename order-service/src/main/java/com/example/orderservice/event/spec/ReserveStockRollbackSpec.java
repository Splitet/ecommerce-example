package com.example.orderservice.event.spec;

import com.example.orderservice.event.model.published.ReserveStockEvent;
import com.kloia.eventapis.api.RollbackSpec;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReserveStockRollbackSpec implements RollbackSpec<ReserveStockEvent> {
	@Override
	public void rollback(ReserveStockEvent event) {
		log.warn("ReserveStockRollbackSpec for :" + event);
	}
}

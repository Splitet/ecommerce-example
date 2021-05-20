package com.example.orderservice.event.spec;

import com.example.orderservice.event.model.published.WaitingStockReleaseEvent;
import io.splitet.core.api.RollbackSpec;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WaitingStockReleaseRollbackSpec implements RollbackSpec<WaitingStockReleaseEvent> {
	@Override
	public void rollback(WaitingStockReleaseEvent event) {
		log.warn("Rollback WaitingStockReleaseEvent for :" + event);
	}
}

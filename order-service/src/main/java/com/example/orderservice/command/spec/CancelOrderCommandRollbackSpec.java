package com.example.orderservice.command.spec;


import com.example.orderservice.command.handler.CancelOrderCommandHandler;
import com.kloia.eventapis.api.RollbackCommandSpec;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CancelOrderCommandRollbackSpec implements RollbackCommandSpec<CancelOrderCommandHandler> {
	public void rollback(String orderId) {
		log.warn("Rollback CancelOrderCommand for :" + orderId);

	}
}
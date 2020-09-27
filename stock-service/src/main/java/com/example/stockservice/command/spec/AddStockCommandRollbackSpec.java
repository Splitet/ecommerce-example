package com.example.stockservice.command.spec;

import com.example.stockservice.command.handler.AddStockCommandHandler;
import com.example.stockservice.resource.model.AddStockDto;
import com.kloia.eventapis.api.RollbackCommandSpec;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddStockCommandRollbackSpec implements RollbackCommandSpec<AddStockCommandHandler> {

	public void rollback(String stockId, AddStockDto event) {
		log.warn("Rolling back AddStockCommandDto for: " + event.toString());
	}
}
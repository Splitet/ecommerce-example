package com.example.stockservice.command.handler;

import com.example.stockservice.command.model.CreateStockCommand;
import com.example.stockservice.util.exception.StockException;
import com.example.stockservice.util.mapper.StockMapper;
import io.splitet.core.api.Command;
import io.splitet.core.api.CommandHandler;
import io.splitet.core.api.EventRepository;
import io.splitet.core.cassandra.ConcurrentEventException;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CreateStockCommandHandler implements CommandHandler {

	private final StockMapper mapper;

	private final EventRepository eventRepository;

	private final AddStockCommandHandler addStockCommandHandler;

	@Command
	public EventKey execute(CreateStockCommand command) throws StockException {
		EventKey eventKey = null;
		try {
			eventKey = eventRepository.recordAndPublish(mapper.toEvent(command));
			eventKey = addStockCommandHandler.execute(mapper.toCommand(eventKey.getEntityId(), command));
		}
		catch (EventStoreException | ConcurrentEventException exception) {
			log.error("Exception is caught while creating stock {}", eventKey.getEntityId());
			throw new StockException(String.format("Can not create stock({})", eventKey.getEntityId()));
		}
		return eventKey;
	}
}

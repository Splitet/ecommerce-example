package com.example.stockservice.command.handler;

import com.example.stockservice.command.model.AddStockCommand;
import com.example.stockservice.event.model.published.StockAddedEvent;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.util.exception.StockException;
import io.splitet.core.api.Command;
import io.splitet.core.api.CommandHandler;
import io.splitet.core.api.EventRepository;
import io.splitet.core.api.ViewQuery;
import io.splitet.core.cassandra.ConcurrentEventException;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class AddStockCommandHandler implements CommandHandler {

    private final ViewQuery<Stock> viewQuery;

    private final EventRepository eventRepository;

    @Command
    public EventKey execute(AddStockCommand command) throws StockException {
        try {
            Stock stock = viewQuery.queryEntity(command.getStockId());

            if (command.getStockToAdd() > 1000000) {
                throw new StockException("Invalid Stock to Add");
            }
            StockAddedEvent event = StockAddedEvent.builder().addedStock(command.getStockToAdd()).build();
            return eventRepository.recordAndPublish(stock.getEventKey(), event);
        }
        catch (EventStoreException | ConcurrentEventException exception) {
            log.error("Exception is caught while adding stock {}", command.getStockId());
            throw new StockException(String.format("Can not add stock({})", command.getStockId()));
        }
    }
}

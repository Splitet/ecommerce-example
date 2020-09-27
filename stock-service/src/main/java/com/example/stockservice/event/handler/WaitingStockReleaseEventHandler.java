package com.example.stockservice.event.handler;

import com.kloia.eventapis.api.EventHandler;
import com.kloia.eventapis.api.EventRepository;
import com.kloia.eventapis.api.ViewQuery;
import com.kloia.eventapis.cassandra.ConcurrencyResolver;
import com.kloia.eventapis.common.EventKey;
import com.example.stockservice.util.exception.StockNotEnoughException;
import com.example.stockservice.event.model.published.StockReleasedEvent;
import com.example.stockservice.event.model.published.StockReservedEvent;
import com.example.stockservice.event.model.received.WaitingStockReleaseEvent;
import com.example.stockservice.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class WaitingStockReleaseEventHandler implements EventHandler<WaitingStockReleaseEvent> {

    private final ViewQuery<Stock> viewQuery;

    private final EventRepository eventRepository;

    @Override
    @KafkaListener(topics = "WaitingStockReleaseEvent", containerFactory = "eventsKafkaListenerContainerFactory")
    public EventKey execute(WaitingStockReleaseEvent event) throws Exception {
        Stock stock = viewQuery.queryEntity(event.getStockId());
        StockReservedEvent stockReservedEvent = viewQuery.queryEventData(event.getStockId(), event.getReservedStockVersion());

        return eventRepository.recordAndPublish(stock,
                StockReleasedEvent.builder().orderId(event.getSender().getEntityId()).numberOfItemsReleased(stockReservedEvent.getNumberOfItemsSold()).build(),
                entityEvent -> new StockConcurrencyResolver());
    }

    private static class StockConcurrencyResolver implements ConcurrencyResolver<StockNotEnoughException> {
        private int maxTry = 100;
        private int currentTry = 0;

        @Override
        public void tryMore() throws StockNotEnoughException {
            if (maxTry <= currentTry++)
                throw new StockNotEnoughException("Cannot allocate stock in Max Try: " + maxTry);
        }

        @Override
        public EventKey calculateNext(EventKey eventKey, int lastVersion) {
            return new EventKey(eventKey.getEntityId(), lastVersion + 1);
        }
    }
}

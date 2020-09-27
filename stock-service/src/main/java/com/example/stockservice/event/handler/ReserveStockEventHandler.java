package com.example.stockservice.event.handler;

import com.kloia.eventapis.api.EventHandler;
import com.kloia.eventapis.api.EventRepository;
import com.kloia.eventapis.api.ViewQuery;
import com.kloia.eventapis.cassandra.ConcurrentEventResolver;
import com.kloia.eventapis.common.EventKey;
import com.kloia.eventapis.exception.EventStoreException;
import com.example.stockservice.util.exception.StockNotEnoughException;
import com.example.stockservice.event.model.received.ReserveStockEvent;
import com.example.stockservice.event.model.published.StockNotEnoughEvent;
import com.example.stockservice.event.model.published.StockReservedEvent;
import com.example.stockservice.entity.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ReserveStockEventHandler implements EventHandler<ReserveStockEvent> {

    private final ViewQuery<Stock> stockQuery;

    private final EventRepository eventRepository;

    @Override
    @KafkaListener(topics = "ReserveStockEvent", containerFactory = "eventsKafkaListenerContainerFactory")
    public EventKey execute(ReserveStockEvent event) throws Exception {
        Stock stock = stockQuery.queryEntity(event.getStockId());
        if (stock.getRemainingStock() >= event.getNumberOfItemsSold()) {
            StockReservedEvent stockReservedEvent = StockReservedEvent.builder()
                    .orderId(event.getSender().getEntityId())
                    .numberOfItemsSold(event.getNumberOfItemsSold())
                    .build();
            try {
                return eventRepository.recordAndPublish(new EventKey(stock.getId(), stock.getVersion() - 1), stockReservedEvent, () -> new StockConcurrencyResolver(stockQuery, event));
            } catch (StockNotEnoughException e) {
                return recordStockNotEnough(event, stock);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return stock.getEventKey();
            }
        } else
            return recordStockNotEnough(event, stock);
    }

    private EventKey recordStockNotEnough(ReserveStockEvent reserveStockEvent, Stock stock) throws EventStoreException, com.kloia.eventapis.cassandra.ConcurrentEventException {
        StockNotEnoughEvent stockNotEnoughEvent =  StockNotEnoughEvent.builder()
                .orderId(reserveStockEvent.getStockId())
                .numberOfItemsSold(stock.getRemainingStock())
                .build();
        return eventRepository.recordAndPublish(stock, stockNotEnoughEvent);
    }

    private static class StockConcurrencyResolver implements ConcurrentEventResolver<StockReservedEvent, StockNotEnoughException> {

        private ViewQuery<Stock> stockQuery;
        private ReserveStockEvent reserveStockEvent;

        private int maxTry = 3;
        private int currentTry = 0;

        public StockConcurrencyResolver(ViewQuery<Stock> stockQuery, ReserveStockEvent reserveStockEvent) {
            this.stockQuery = stockQuery;
            this.reserveStockEvent = reserveStockEvent;
        }

        @Override
        public void tryMore() throws StockNotEnoughException {
            if (maxTry <= currentTry++)
                throw new StockNotEnoughException("Cannot allocate stock in Max Try: " + maxTry);
        }

        @Override
        public Pair<EventKey, StockReservedEvent> calculateNext(StockReservedEvent failedEvent, EventKey failedEventKey, int lastVersion) throws StockNotEnoughException, EventStoreException {
            Stock stock = stockQuery.queryEntity(failedEventKey.getEntityId());
            if (stock.getRemainingStock() < reserveStockEvent.getNumberOfItemsSold()) {
                throw new StockNotEnoughException("Out Of Stock Event");
            } else {
                return new ImmutablePair<>(new EventKey(failedEventKey.getEntityId(), stock.getVersion() + 1), failedEvent);
            }
        }
    }
}

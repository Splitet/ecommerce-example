package com.example.orderservice.event.handler;

import com.kloia.eventapis.api.EventHandler;
import com.kloia.eventapis.api.EventRepository;
import com.kloia.eventapis.api.ViewQuery;
import com.kloia.eventapis.cassandra.ConcurrentEventException;
import com.kloia.eventapis.common.EventKey;
import com.kloia.eventapis.exception.EventStoreException;
import com.example.orderservice.event.model.published.PaymentProcessEvent;
import com.example.orderservice.event.model.received.StockReservedEvent;
import com.example.orderservice.entity.OrderState;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.PaymentInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class StockReservedEventHandler implements EventHandler<StockReservedEvent> {

    private final ViewQuery<Order> viewQuery;

    private final EventRepository eventRepository;

    @Override
    @KafkaListener(topics = "StockReservedEvent", containerFactory = "eventsKafkaListenerContainerFactory")
    public EventKey execute(StockReservedEvent dto) throws EventStoreException, ConcurrentEventException {
        Order order = viewQuery.queryEntity(dto.getOrderId());

        if (order.getState() == OrderState.RESERVING_STOCK) {
            PaymentProcessEvent event = PaymentProcessEvent.builder()
                    .orderId(order.getId())
                    .reservedStockVersion(dto.getSender().getVersion())
                    .paymentInformation(new PaymentInformation(order.getAmount(), order.getPaymentAddress(), order.getCardInformation()))
                    .build();
            log.info("Payment is processing : " + dto);
            return eventRepository.recordAndPublish(order, event);
        } else
            throw new EventStoreException("Order state is not valid for this Operation: " + dto);
    }
}

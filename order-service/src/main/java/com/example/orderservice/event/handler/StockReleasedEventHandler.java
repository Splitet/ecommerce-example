package com.example.orderservice.event.handler;

import com.example.orderservice.client.PaymentClient;
import com.kloia.eventapis.api.EventHandler;
import com.kloia.eventapis.api.EventRepository;
import com.kloia.eventapis.api.ViewQuery;
import com.kloia.eventapis.cassandra.ConcurrentEventException;
import com.kloia.eventapis.common.EventKey;
import com.kloia.eventapis.exception.EventStoreException;
import com.example.orderservice.client.ReturnPaymentDto;
import com.example.orderservice.event.model.published.OrderCancelledEvent;
import com.example.orderservice.event.model.received.StockReleasedEvent;
import com.example.orderservice.entity.OrderState;
import com.example.orderservice.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class StockReleasedEventHandler implements EventHandler<StockReleasedEvent> {

    private final EventRepository eventRepository;
    private final ViewQuery<Order> orderQuery;
    private final PaymentClient paymentClient;

    @KafkaListener(topics = "StockReleasedEvent", containerFactory = "eventsKafkaListenerContainerFactory")
    public EventKey execute(StockReleasedEvent dto) throws EventStoreException, ConcurrentEventException {
        Order order = orderQuery.queryEntity(dto.getOrderId());

        if (order.getState() == OrderState.RELEASING_STOCK) {
            log.info("Payment is processing : " + dto);
            EventKey eventKey = eventRepository.recordAndPublish(order, OrderCancelledEvent.builder().build());
            paymentClient.returnPayment(order.getPaymentId(), new ReturnPaymentDto(order.getId()));
            return eventKey;
        } else
            throw new EventStoreException("Order state is not valid for this Operation: " + dto);
    }
}

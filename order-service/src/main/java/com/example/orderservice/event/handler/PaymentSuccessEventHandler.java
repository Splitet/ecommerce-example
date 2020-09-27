package com.example.orderservice.event.handler;

import com.kloia.eventapis.api.EventHandler;
import com.kloia.eventapis.api.EventRepository;
import com.kloia.eventapis.api.ViewQuery;
import com.kloia.eventapis.cassandra.ConcurrentEventException;
import com.kloia.eventapis.common.EventKey;
import com.kloia.eventapis.exception.EventStoreException;
import com.example.orderservice.event.model.published.OrderPaidEvent;
import com.example.orderservice.event.model.received.PaymentSuccessEvent;
import com.example.orderservice.entity.OrderState;
import com.example.orderservice.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentSuccessEventHandler implements EventHandler<PaymentSuccessEvent> {

    private final ViewQuery<Order> orderQuery;

    private final EventRepository eventRepository;

    @Override
    @KafkaListener(topics = "PaymentSuccessEvent", containerFactory = "eventsKafkaListenerContainerFactory")
    public EventKey execute(PaymentSuccessEvent dto) throws EventStoreException, ConcurrentEventException {
        Order order = orderQuery.queryEntity(dto.getOrderId());

        if (order.getState() == OrderState.PAYMENT_READY) {
            log.info("Payment is processing : " + dto);
            return eventRepository.recordAndPublish(order.getEventKey(), OrderPaidEvent.builder().paymentId(dto.getSender().getEntityId()).build());
        } else
            throw new EventStoreException("Order state is not valid for this Operation: " + dto);
    }
}

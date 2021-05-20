package com.example.paymentservice.event.handler;

import com.example.paymentservice.event.model.published.PaymentFailedEvent;
import com.example.paymentservice.event.model.published.PaymentSuccessEvent;
import com.example.paymentservice.event.model.received.PaymentProcessEvent;
import com.example.paymentservice.util.exception.PaymentException;
import io.splitet.core.api.EventHandler;
import io.splitet.core.api.EventRepository;
import io.splitet.core.cassandra.ConcurrentEventException;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class PaymentProcessEventHandler implements EventHandler<PaymentProcessEvent> {

	private final EventRepository eventRepository;

	@Autowired
	public PaymentProcessEventHandler(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@KafkaListener(topics = "PaymentProcessEvent", containerFactory = "eventsKafkaListenerContainerFactory")
	public EventKey execute(PaymentProcessEvent event) throws EventStoreException, ConcurrentEventException, PaymentException {
		PaymentSuccessEvent paymentSuccessEvent = PaymentSuccessEvent.builder()
				.paymentAddress(event.getPaymentInformation().getPaymentAddress())
				.orderId(event.getSender().getEntityId())
				.build();
		if (event.getPaymentInformation().getAmount() > 2000)
			throw new PaymentException("Bla Bla");
		if (event.getPaymentInformation().getAmount() > 1000) {
			PaymentFailedEvent paymentFailedEvent = PaymentFailedEvent.builder()
					.orderId(event.getSender().getEntityId())
					.paymentAddress(event.getPaymentInformation().getPaymentAddress())
					.build();
			return eventRepository.recordAndPublish(paymentFailedEvent);
		}

		return eventRepository.recordAndPublish(paymentSuccessEvent);

	}
}

package com.example.orderservice.command.handler;

import com.example.orderservice.command.model.ProcessOrderCommand;
import com.example.orderservice.event.model.published.ReserveStockEvent;
import com.example.orderservice.util.exception.OrderException;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
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
public class ProcessOrderCommandHandler implements CommandHandler {

	private final ViewQuery<Order> viewQuery;

	private final EventRepository eventRepository;

	@Command
	public EventKey execute(ProcessOrderCommand command) throws OrderException {
		try {
			Order order = viewQuery.queryEntity(command.getOrderId());
			if (order.getState() != OrderState.INITIAL) {
				throw new OrderException("Order state is not valid for this operation" + command);
			}
			ReserveStockEvent event = ReserveStockEvent.builder()
					.stockId(order.getStockId())
					.numberOfItemsSold(order.getOrderAmount())
					.paymentInformation(command.getPaymentInformation())
					.build();
			return eventRepository.recordAndPublish(order, event);
		}
		catch (EventStoreException | ConcurrentEventException exception) {
			log.error("Exception is caught while processing order {}", command.getOrderId());
			throw new OrderException(String.format("Can not process order({})", command.getOrderId()));
		}
	}
}

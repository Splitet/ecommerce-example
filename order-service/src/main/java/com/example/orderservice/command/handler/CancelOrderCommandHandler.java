package com.example.orderservice.command.handler;

import com.example.orderservice.event.model.published.WaitingStockReleaseEvent;
import com.example.orderservice.util.exception.OrderException;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.kloia.eventapis.api.Command;
import com.kloia.eventapis.api.CommandHandler;
import com.kloia.eventapis.api.EventRepository;
import com.kloia.eventapis.api.ViewQuery;
import com.kloia.eventapis.cassandra.ConcurrentEventException;
import com.kloia.eventapis.common.EventKey;
import com.kloia.eventapis.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelOrderCommandHandler implements CommandHandler {

	private final ViewQuery<Order> orderQuery;

	private final EventRepository eventRepository;

	@Command
	public EventKey execute(String orderId) throws OrderException {
		try {
			Order order = orderQuery.queryEntity(orderId);
			if (order.getState() != OrderState.PAID) {
				throw new OrderException("Order state is not valid for this operation" + order);
			}
			WaitingStockReleaseEvent waitingStockReleaseEvent = WaitingStockReleaseEvent.builder()
					.stockId(order.getStockId()).reservedStockVersion(order.getReservedStockVersion()).build();
			return eventRepository.recordAndPublish(order, waitingStockReleaseEvent);
		}
		catch (EventStoreException | ConcurrentEventException exception) {
			log.error("Exception is caught while cancelling order {}", orderId);
			throw new OrderException(String.format("Can not cancel order({})", orderId));
		}
	}
}



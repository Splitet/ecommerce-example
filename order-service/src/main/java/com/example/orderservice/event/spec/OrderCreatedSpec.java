package com.example.orderservice.event.spec;

import com.example.orderservice.event.model.published.OrderCreatedEvent;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class OrderCreatedSpec extends EntityFunctionSpec<Order, OrderCreatedEvent> {
	public OrderCreatedSpec() {
		super((order, event) -> {
			OrderCreatedEvent createOrderCommandDto = event.getEventData();
			order.setStockId(createOrderCommandDto.getStockId());
			order.setOrderAmount(createOrderCommandDto.getOrderAmount());
			order.setDescription(createOrderCommandDto.getDescription());
			order.setState(OrderState.INITIAL);
			return order;
		});
	}
}

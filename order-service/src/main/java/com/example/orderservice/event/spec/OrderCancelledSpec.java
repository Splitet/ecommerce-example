package com.example.orderservice.event.spec;


import com.example.orderservice.event.model.published.OrderCancelledEvent;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class OrderCancelledSpec extends EntityFunctionSpec<Order, OrderCancelledEvent> {
	public OrderCancelledSpec() {
		super((order, event) -> {
			order.setState(OrderState.CANCELLED);
			return order;
		});
	}
}
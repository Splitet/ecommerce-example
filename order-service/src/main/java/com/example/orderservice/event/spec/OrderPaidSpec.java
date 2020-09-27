package com.example.orderservice.event.spec;

import com.example.orderservice.event.model.published.OrderPaidEvent;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class OrderPaidSpec extends EntityFunctionSpec<Order, OrderPaidEvent> {
	public OrderPaidSpec() {
		super((order, event) -> {
			OrderPaidEvent eventData = event.getEventData();
			order.setPaymentId(eventData.getPaymentId());
			order.setState(OrderState.PAID);
			return order;
		});
	}
}
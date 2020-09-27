package com.example.orderservice.event.spec;


import com.example.orderservice.event.model.published.PaymentProcessEvent;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class PaymentProcessSpec extends EntityFunctionSpec<Order, PaymentProcessEvent> {
	public PaymentProcessSpec() {
		super((order, event) -> {
			PaymentProcessEvent eventData = event.getEventData();
			order.setReservedStockVersion(eventData.getReservedStockVersion());
			order.setState(OrderState.PAYMENT_READY);
			return order;
		});
	}
}
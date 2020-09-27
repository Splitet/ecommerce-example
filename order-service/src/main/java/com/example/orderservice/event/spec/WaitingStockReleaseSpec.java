package com.example.orderservice.event.spec;

import com.example.orderservice.event.model.published.WaitingStockReleaseEvent;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class WaitingStockReleaseSpec extends EntityFunctionSpec<Order, WaitingStockReleaseEvent> {
	public WaitingStockReleaseSpec() {
		super((order, event) -> {
			WaitingStockReleaseEvent eventData = event.getEventData();
			order.setState(OrderState.RELEASING_STOCK);
			return order;
		});
	}
}

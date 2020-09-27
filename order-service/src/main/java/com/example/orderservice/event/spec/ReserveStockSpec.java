package com.example.orderservice.event.spec;


import com.example.orderservice.event.model.published.ReserveStockEvent;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderState;
import com.example.orderservice.entity.PaymentInformation;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class ReserveStockSpec extends EntityFunctionSpec<Order, ReserveStockEvent> {
	public ReserveStockSpec() {
		super((order, event) -> {
			ReserveStockEvent eventData = event.getEventData();
			PaymentInformation paymentInformation = eventData.getPaymentInformation();
			order.setPaymentAddress(paymentInformation.getPaymentAddress());
			order.setAmount(paymentInformation.getAmount());
			order.setCardInformation(paymentInformation.getCardInformation());
			order.setState(OrderState.RESERVING_STOCK);
			return order;
		});
	}
}

package com.example.paymentservice.event.spec;

import com.example.paymentservice.event.model.published.PaymentSuccessEvent;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.PaymentState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class PaymentSuccessSpec extends EntityFunctionSpec<Payment, PaymentSuccessEvent> {
	public PaymentSuccessSpec() {
		super((payment, event) -> {
			PaymentSuccessEvent createOrderCommandDto = event.getEventData();
			payment.setOrderId(createOrderCommandDto.getOrderId());
			payment.setAmount(createOrderCommandDto.getAmount());
			payment.setCardInformation(createOrderCommandDto.getCardInformation());
			payment.setPaymentAddress(createOrderCommandDto.getPaymentAddress());
			payment.setState(PaymentState.PAID);
			return payment;
		});
	}
}

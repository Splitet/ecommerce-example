package com.example.paymentservice.event.spec;

import com.example.paymentservice.event.model.published.PaymentReturnedEvent;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.PaymentState;
import com.kloia.eventapis.view.EntityFunctionSpec;

import org.springframework.stereotype.Component;

@Component
public class PaymentReturnedSpec extends EntityFunctionSpec<Payment, PaymentReturnedEvent> {
	public PaymentReturnedSpec() {
		super((payment, event) -> {
			PaymentReturnedEvent eventData = event.getEventData();
			payment.setAmount(payment.getAmount() - eventData.getAmount());
			payment.setState(PaymentState.RETURN);
			return payment;
		});
	}
}

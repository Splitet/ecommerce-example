package com.example.paymentservice.resource.api;

import com.example.paymentservice.command.handler.ReturnPaymentCommandHandler;
import com.example.paymentservice.resource.PaymentCommandResource;
import com.example.paymentservice.resource.model.ReturnPaymentDto;
import com.example.paymentservice.util.exception.PaymentException;
import com.example.paymentservice.util.mapper.PaymentMapper;
import com.kloia.eventapis.common.EventKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentCommandApiController implements PaymentCommandResource {

	private final PaymentMapper mapper;

	private final ReturnPaymentCommandHandler returnPaymentCommandHandler;

	@Override
	public EventKey returnPayment(String paymentId, ReturnPaymentDto dto) throws PaymentException {
		return returnPaymentCommandHandler.execute(mapper.toCommand(paymentId, dto));
	}
}

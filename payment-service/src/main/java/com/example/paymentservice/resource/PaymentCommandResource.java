package com.example.paymentservice.resource;

import javax.validation.Valid;

import com.example.paymentservice.resource.model.ReturnPaymentDto;
import com.example.paymentservice.util.exception.PaymentException;
import com.kloia.eventapis.common.EventKey;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "Payment Resource", tags = "payments")
public interface PaymentCommandResource {

	@PostMapping(value = "/payments/{paymentId}/return")
	EventKey returnPayment(@PathVariable("paymentId") String paymentId, @RequestBody @Valid ReturnPaymentDto dto) throws PaymentException;
}

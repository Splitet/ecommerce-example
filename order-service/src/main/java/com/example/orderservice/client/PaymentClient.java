package com.example.orderservice.client;

import io.splitet.core.common.EventKey;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service",url = "${payment-client-url}")
public interface PaymentClient {

    @PostMapping(value = "/payments/{paymentId}/return", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EventKey returnPayment(@PathVariable("paymentId") String paymentId, @RequestBody ReturnPaymentDto returnPaymentDto);

}

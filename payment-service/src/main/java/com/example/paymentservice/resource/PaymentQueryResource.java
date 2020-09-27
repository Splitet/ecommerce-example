package com.example.paymentservice.resource;

import java.util.List;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.util.exception.PaymentException;
import com.kloia.eventapis.cassandra.EntityEvent;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = "Payment Resource", tags = "payments")
public interface PaymentQueryResource {

	@GetMapping(value = "/payments/{paymentId}")
	Payment findPaymentById(@PathVariable("paymentId") String paymentId);

	@GetMapping(value = "/payments/{paymentId}/history")
	List<EntityEvent> findPaymentHistoriesByPaymentId(@PathVariable("paymentId") String paymentId) throws PaymentException;

	@GetMapping(value = "/payments/{paymentId}/{version}")
	Payment findPaymentByIdAndVersion(@PathVariable("paymentId") String paymentId, @PathVariable("version") Integer version) throws PaymentException;

	@GetMapping(value = "/payments")
	Page<Payment> findPayments(@QuerydslPredicate(root = Payment.class) Predicate predicate, Pageable pageable);
}

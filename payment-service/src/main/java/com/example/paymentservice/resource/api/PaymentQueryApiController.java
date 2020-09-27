package com.example.paymentservice.resource.api;

import java.util.List;
import java.util.Objects;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.resource.PaymentQueryResource;
import com.example.paymentservice.util.exception.PaymentException;
import com.kloia.eventapis.api.ViewQuery;
import com.kloia.eventapis.cassandra.EntityEvent;
import com.kloia.eventapis.exception.EventStoreException;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentQueryApiController implements PaymentQueryResource {

	private final PaymentRepository repository;

	private final ViewQuery<Payment> viewQuery;

	@Override
	@SneakyThrows
	public Payment findPaymentById(String paymentId) {
		return repository.findById(paymentId).orElseThrow(() -> new PaymentException(String.format("There is no payment with %s", paymentId)));
	}

	@Override
	public List<EntityEvent> findPaymentHistoriesByPaymentId(String paymentId) throws PaymentException {
		List<EntityEvent> history;
		try {
			history = viewQuery.queryHistory(paymentId);
		}
		catch (EventStoreException exception) {
			log.error("Exception is caught while querying payment with {}", paymentId);
			throw new PaymentException(String.format("There is no payment with %s", paymentId));
		}
		return history;
	}

	@Override
	public Payment findPaymentByIdAndVersion(String paymentId, Integer version) throws PaymentException {
		Payment payment;
		try {
			payment = Objects.isNull(version) ? viewQuery.queryEntity(paymentId) : viewQuery
					.queryEntity(paymentId, version);
		}
		catch (EventStoreException exception) {
			log.error("Exception is caught while querying payment with {}, {}", paymentId, version);
			throw new PaymentException(String
					.format("There is no payment with %s id and %s version", paymentId, version));
		}
		return payment;
	}

	@Override
	public Page<Payment> findPayments(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}
}
package com.example.paymentservice.command.handler;

import com.example.paymentservice.command.model.ReturnPaymentCommand;
import com.example.paymentservice.util.exception.PaymentException;
import io.splitet.core.api.Command;
import io.splitet.core.api.CommandHandler;
import io.splitet.core.api.EventRepository;
import io.splitet.core.api.ViewQuery;
import io.splitet.core.cassandra.ConcurrentEventException;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import com.example.paymentservice.resource.model.ReturnPaymentDto;
import com.example.paymentservice.event.model.published.PaymentReturnedEvent;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.PaymentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Slf4j
@Component
@RequiredArgsConstructor
public class ReturnPaymentCommandHandler implements CommandHandler {

    private final EventRepository eventRepository;

    private final ViewQuery<Payment> paymentViewQuery;

    @Command
    public EventKey execute(ReturnPaymentCommand command) throws PaymentException {
        Payment payment = null;
        try {
            payment = paymentViewQuery.queryEntity(command.getPaymentId());
            if (payment.getState() != PaymentState.PAID) {
                throw new PaymentException("Payment state is not valid for this Operation: " + command);
            }
            PaymentReturnedEvent event = PaymentReturnedEvent.builder().orderId(payment.getOrderId()).amount(payment.getAmount()).build();
            return eventRepository.recordAndPublish(payment, event);
        }
        catch (EventStoreException | ConcurrentEventException exception) {
            log.error("Exception is caught while processing order {}", command.getPaymentId());
            throw new PaymentException(String.format("Can not return payment({})", command.getPaymentId()));
        }
    }
}

package com.example.orderservice.command.handler;

import com.example.orderservice.command.model.CreateOrderCommand;
import com.example.orderservice.util.exception.OrderException;
import com.example.orderservice.util.mapper.OrderMapper;
import io.splitet.core.api.Command;
import io.splitet.core.api.CommandHandler;
import io.splitet.core.api.EventRepository;
import io.splitet.core.cassandra.ConcurrentEventException;
import io.splitet.core.common.EventKey;
import io.splitet.core.exception.EventStoreException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderCommandHandler implements CommandHandler {

    private final OrderMapper mapper;

    private final EventRepository eventRepository;


    @Command
    public EventKey execute(CreateOrderCommand command) throws OrderException {
        EventKey eventKey;
        try {
            eventKey = eventRepository.recordAndPublish(mapper.toEvent(command));
        }
        catch (EventStoreException | ConcurrentEventException exception) {
            log.error("Exception is caught while creating account");
            throw new OrderException(String.format("Account can not be created", command));
        }
        return eventKey;
    }
}

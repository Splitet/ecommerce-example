package com.example.orderservice.resource.api;

import java.util.List;
import java.util.Objects;

import com.example.orderservice.util.exception.OrderException;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.resource.OrderQueryResource;
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
public class OrderQueryApiController implements OrderQueryResource {

    private final OrderRepository repository;

    private final ViewQuery<Order> viewQuery;

    @Override
    @SneakyThrows
    public Order findOrderById(String orderId) {
        return repository.findById(orderId).orElseThrow(() -> new OrderException(String.format("There is no order with %s", orderId)));
    }

    @Override
    public List<EntityEvent> findOrderHistoriesByOrderId(String orderId) throws OrderException {
        List<EntityEvent> history;
        try {
            history = viewQuery.queryHistory(orderId);
        }
        catch (EventStoreException exception) {
            log.error("Exception is caught while querying order with {}", orderId);
            throw new OrderException(String.format("There is no order with %s", orderId));
        }
        return history;
    }

    @Override
    public Order findOrderByIdAndVersion(String orderId, Integer version) throws OrderException {
        Order order;
        try {
            order = Objects.isNull(version) ? viewQuery.queryEntity(orderId) : viewQuery
                    .queryEntity(orderId, version);
        }
        catch (EventStoreException exception) {
            log.error("Exception is caught while querying order with {}, {}", orderId, version);
            throw new OrderException(String
                    .format("There is no order with %s id and %s version", orderId, version));
        }
        return order;
    }

    @Override
    public Page<Order> findOrders(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }
}


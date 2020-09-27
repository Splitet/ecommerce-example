package com.example.orderservice.resource;

import java.util.List;

import com.example.orderservice.util.exception.OrderException;
import com.example.orderservice.entity.Order;
import com.kloia.eventapis.cassandra.EntityEvent;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = "Order Resource", tags = "orders")
public interface OrderQueryResource {

	@GetMapping(value = "/orders/{orderId}")
	Order findOrderById(@PathVariable("orderId") String orderId);

	@GetMapping(value = "/orders/{orderId}/history")
	List<EntityEvent> findOrderHistoriesByOrderId(@PathVariable("orderId") String orderId) throws OrderException;

	@GetMapping(value = "/orders/{orderId}/{version}")
	Order findOrderByIdAndVersion(@PathVariable("orderId") String orderId, @PathVariable("version") Integer version) throws OrderException;

	@GetMapping(value = "/orders")
	Page<Order> findOrders(@QuerydslPredicate(root = Order.class) Predicate predicate, Pageable pageable);
}

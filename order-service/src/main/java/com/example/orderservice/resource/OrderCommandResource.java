package com.example.orderservice.resource;

import javax.validation.Valid;

import com.example.orderservice.util.exception.OrderException;
import com.example.orderservice.resource.model.CreateOrderDto;
import com.example.orderservice.resource.model.ProcessOrderDto;
import com.kloia.eventapis.common.EventKey;
import com.kloia.eventapis.view.Entity;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(value = "Order Resource", tags = "orders")
public interface OrderCommandResource {

	@PostMapping(value = "/orders")
	EventKey createOrder(@RequestBody @Valid CreateOrderDto dto) throws OrderException;

	@DeleteMapping(value = "/orders/{orderId}")
	EventKey cancelOrder(@PathVariable("orderId") String orderId) throws OrderException;

	@PostMapping(value = "/orders/{orderId}/process")
	EventKey processOrder(@PathVariable("orderId") String orderId, @RequestBody @Valid ProcessOrderDto dto) throws OrderException;

	@PostMapping(value = "/orders/{entityId}/{version}/migrate")
	Entity migrate(
			@PathVariable("entityId") String entityId, @PathVariable("version") Integer version,
			@RequestParam("snapshot") Boolean snapshot, @RequestBody CreateOrderDto dto) throws OrderException;
}

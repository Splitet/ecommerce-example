package com.example.orderservice.resource.api;

import javax.validation.Valid;

import com.example.orderservice.command.handler.CancelOrderCommandHandler;
import com.example.orderservice.command.handler.CreateOrderCommandHandler;
import com.example.orderservice.command.handler.ProcessOrderCommandHandler;
import com.example.orderservice.event.model.published.OrderCreatedEvent;
import com.example.orderservice.util.exception.OrderException;
import com.example.orderservice.util.mapper.OrderMapper;
import com.example.orderservice.resource.OrderCommandResource;
import com.example.orderservice.resource.model.CreateOrderDto;
import com.example.orderservice.resource.model.ProcessOrderDto;
import com.kloia.eventapis.common.EventKey;
import com.kloia.eventapis.exception.EventStoreException;
import com.kloia.eventapis.spring.configuration.DataMigrationService;
import com.kloia.eventapis.view.Entity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderCommandApiController implements OrderCommandResource {

	private final OrderMapper mapper;

	private final DataMigrationService dataMigrationService;

	private final CreateOrderCommandHandler createOrderCommandHandler;

	private final CancelOrderCommandHandler cancelOrderCommandHandler;

	private final ProcessOrderCommandHandler processOrderCommandHandler;

	@Override
	public EventKey createOrder(@Valid CreateOrderDto dto) throws OrderException {
		return createOrderCommandHandler.execute(mapper.toCommand(dto));
	}

	@Override
	public EventKey cancelOrder(String orderId) throws OrderException {
		return cancelOrderCommandHandler.execute(orderId);
	}

	@Override
	public EventKey processOrder(String orderId, ProcessOrderDto dto) throws OrderException {
		return processOrderCommandHandler.execute(mapper.toCommand(orderId, dto));
	}

	@Override
	public Entity migrate(String entityId, Integer version, Boolean snapshot, CreateOrderDto dto) throws OrderException {
		try {
			OrderCreatedEvent event = OrderCreatedEvent.builder()
					.stockId(dto.getStockId())
					.orderAmount(dto.getOrderAmount())
					.description(dto.getDescription())
					.build();
			return dataMigrationService.updateEvent(new EventKey(entityId, version), true, event);
		}
		catch (EventStoreException exception) {
			log.error("Exception is caught while migrating records for {}", entityId);
			throw new OrderException(String.format("Can not migrate records for order({})", entityId));
		}
	}

}


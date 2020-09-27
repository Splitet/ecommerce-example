package com.example.orderservice.util.mapper;

import com.example.orderservice.command.model.CreateOrderCommand;
import com.example.orderservice.command.model.ProcessOrderCommand;
import com.example.orderservice.event.model.published.OrderCreatedEvent;
import com.example.orderservice.resource.model.CreateOrderDto;
import com.example.orderservice.resource.model.ProcessOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	CreateOrderCommand toCommand(CreateOrderDto dto);

	OrderCreatedEvent toEvent(CreateOrderCommand command);

	@Mappings({
			@Mapping(source = "orderId", target = "orderId"),
			@Mapping(source = "dto.paymentInformation", target = "paymentInformation")
	})
	ProcessOrderCommand toCommand(String orderId, ProcessOrderDto dto);

}

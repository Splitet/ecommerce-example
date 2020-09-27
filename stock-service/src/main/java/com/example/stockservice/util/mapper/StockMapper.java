package com.example.stockservice.util.mapper;

import com.example.stockservice.command.model.AddStockCommand;
import com.example.stockservice.command.model.CreateStockCommand;
import com.example.stockservice.event.model.published.StockCreatedEvent;
import com.example.stockservice.resource.model.AddStockDto;
import com.example.stockservice.resource.model.CreateStockDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface StockMapper {

	@Mappings({
			@Mapping(source = "stockId", target = "stockId"),
			@Mapping(source = "dto.stockToAdd", target = "stockToAdd")
	})
	AddStockCommand toCommand(String stockId, AddStockDto dto);

	CreateStockCommand toCommand(CreateStockDto dto);

	StockCreatedEvent toEvent(CreateStockCommand command);

	@Mappings({
			@Mapping(target = "stockId", source = "entityId"),
			@Mapping(target = "stockToAdd", source = "command.remainingStock")
	})
	AddStockCommand toCommand(String entityId, CreateStockCommand command);

}

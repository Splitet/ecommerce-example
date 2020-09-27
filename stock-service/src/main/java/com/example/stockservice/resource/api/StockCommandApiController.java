package com.example.stockservice.resource.api;

import com.example.stockservice.command.handler.AddStockCommandHandler;
import com.example.stockservice.command.handler.CreateStockCommandHandler;
import com.example.stockservice.resource.StockCommandResource;
import com.example.stockservice.resource.model.AddStockDto;
import com.example.stockservice.resource.model.CreateStockDto;
import com.example.stockservice.util.exception.StockException;
import com.example.stockservice.util.mapper.StockMapper;
import com.kloia.eventapis.common.EventKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StockCommandApiController implements StockCommandResource {

	private final StockMapper mapper;

	private final AddStockCommandHandler addStockCommandHandler;

	private final CreateStockCommandHandler createStockCommandHandler;

	@Override
	public EventKey addStock(String stockId, AddStockDto dto) throws StockException {
		return addStockCommandHandler.execute(mapper.toCommand(stockId, dto));
	}

	@Override
	public EventKey createStock(CreateStockDto dto) throws StockException{
		return createStockCommandHandler.execute(mapper.toCommand(dto));
	}
}

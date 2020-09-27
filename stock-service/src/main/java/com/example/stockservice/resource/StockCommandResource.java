package com.example.stockservice.resource;

import com.example.stockservice.resource.model.AddStockDto;
import com.example.stockservice.resource.model.CreateStockDto;
import com.example.stockservice.util.exception.StockException;
import com.kloia.eventapis.common.EventKey;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "Stock Resource", tags = "stocks")
public interface StockCommandResource {

	@PostMapping(value = "/stocks/{stockId}")
	EventKey addStock(@PathVariable String stockId, @RequestBody AddStockDto dto) throws StockException;

	@PostMapping(value = "/stocks")
	EventKey createStock(@RequestBody CreateStockDto dto) throws StockException;
}

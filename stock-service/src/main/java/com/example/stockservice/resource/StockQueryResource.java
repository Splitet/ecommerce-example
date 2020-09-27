package com.example.stockservice.resource;

import java.util.List;

import com.example.stockservice.entity.Stock;
import com.example.stockservice.util.exception.StockException;
import com.kloia.eventapis.cassandra.EntityEvent;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api(value = "Stock Resource", tags = "stocks")
public interface StockQueryResource {

	@GetMapping(value = "/stocks/{stockId}")
	Stock findStockById(@PathVariable("stockId") String stockId);

	@GetMapping(value = "/stocks/{stockId}/history")
	List<EntityEvent> findStockHistoriesByStockId(@PathVariable("stockId") String stockId) throws StockException;

	@GetMapping(value = "/stocks/{stockId}/{version}")
	Stock findStockByIdAndVersion(@PathVariable("stockId") String stockId, @PathVariable("version") Integer version) throws StockException;

	@GetMapping(value = "/stocks")
	Page<Stock> findStocks(@QuerydslPredicate(root = Stock.class) Predicate predicate, Pageable pageable);
	
}

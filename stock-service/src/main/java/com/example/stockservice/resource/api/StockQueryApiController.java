package com.example.stockservice.resource.api;

import com.example.stockservice.resource.StockQueryResource;
import com.example.stockservice.util.exception.StockException;
import io.splitet.core.api.ViewQuery;
import io.splitet.core.cassandra.EntityEvent;
import io.splitet.core.exception.EventStoreException;
import com.example.stockservice.entity.Stock;
import com.example.stockservice.repository.StockRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@RequiredArgsConstructor
public class StockQueryApiController implements StockQueryResource {

    private final ViewQuery<Stock> viewQuery;
    
    private final StockRepository repository;

    @Override
    @SneakyThrows
    public Stock findStockById(String stockId) {
        return repository.findById(stockId).orElseThrow(() -> new StockException(String.format("There is no stock with %s", stockId)));
    }

    @Override
    public List<EntityEvent> findStockHistoriesByStockId(String stockId) throws StockException {
        List<EntityEvent> history;
        try {
            history = viewQuery.queryHistory(stockId);
        }
        catch (EventStoreException exception) {
            log.error("Exception is caught while querying stock with {}", stockId);
            throw new StockException(String.format("There is no stock with %s", stockId));
        }
        return history;
    }

    @Override
    public Stock findStockByIdAndVersion(String stockId, Integer version) throws StockException {
        Stock stock;
        try {
            stock = Objects.isNull(version) ? viewQuery.queryEntity(stockId) : viewQuery
                    .queryEntity(stockId, version);
        }
        catch (EventStoreException exception) {
            log.error("Exception is caught while querying stock with {}, {}", stockId, version);
            throw new StockException(String
                    .format("There is no stock with %s id and %s version", stockId, version));
        }
        return stock;
    }

    @Override
    public Page<Stock> findStocks(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }
}


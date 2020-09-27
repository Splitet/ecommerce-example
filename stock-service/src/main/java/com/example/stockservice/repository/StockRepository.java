package com.example.stockservice.repository;

import com.example.stockservice.entity.Stock;
import com.kloia.eventapis.view.SnapshotRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String>, QuerydslPredicateExecutor<Stock>, SnapshotRepository<Stock, String> {

}

package com.example.orderservice.repository;


import com.example.orderservice.entity.Order;
import com.kloia.eventapis.view.SnapshotRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, QuerydslPredicateExecutor<Order>, SnapshotRepository<Order, String> {

}

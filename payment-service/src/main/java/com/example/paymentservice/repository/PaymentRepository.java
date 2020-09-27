package com.example.paymentservice.repository;


import com.example.paymentservice.entity.Payment;
import com.kloia.eventapis.view.SnapshotRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>, QuerydslPredicateExecutor<Payment>, SnapshotRepository<Payment, String> {

}

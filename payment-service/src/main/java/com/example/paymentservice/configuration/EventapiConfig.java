package com.example.paymentservice.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kloia.eventapis.api.EventRepository;
import com.kloia.eventapis.api.IUserContext;
import com.kloia.eventapis.api.RollbackSpec;
import com.kloia.eventapis.api.ViewQuery;
import com.kloia.eventapis.cassandra.CassandraEventRecorder;
import com.kloia.eventapis.cassandra.CassandraSession;
import com.kloia.eventapis.cassandra.CassandraViewQuery;
import com.kloia.eventapis.common.EventRecorder;
import com.kloia.eventapis.common.OperationContext;
import com.kloia.eventapis.core.CompositeRepositoryImpl;
import com.kloia.eventapis.kafka.IOperationRepository;
import com.kloia.eventapis.spring.configuration.DataMigrationService;
import com.kloia.eventapis.spring.configuration.EventApisConfiguration;
import com.kloia.eventapis.view.AggregateListener;
import com.kloia.eventapis.view.EntityFunctionSpec;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class EventapiConfig {

    @Autowired
    CassandraSession cassandraSession;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OperationContext operationContext;

    @Bean
    public AggregateListener snapshotRecpayment(ViewQuery<Payment> paymentViewRepository, // View Query with Function Specs
            EventRepository paymentEventRepository, // Event Repository to mark failed events
            PaymentRepository paymentRepository,      // Jpa Repository to record snapshots
            Optional<List<RollbackSpec>> rollbackSpecs // Custom Rollback Specs for Event Failures
    ) {
        return new AggregateListener(paymentViewRepository, paymentEventRepository, paymentRepository, rollbackSpecs
                .orElseGet(ArrayList::new), objectMapper);
    }

    @Bean
    public ViewQuery<Payment> paymentViewRepository(List<EntityFunctionSpec<Payment, ?>> functionSpecs, EventApisConfiguration eventApisConfiguration) {
        return new CassandraViewQuery<>(
                eventApisConfiguration.getTableNameForEvents("payment"),
                cassandraSession, objectMapper, functionSpecs);
    }

    @Bean
    public EventRecorder paymentPersistentEventRepository(EventApisConfiguration eventApisConfiguration, IUserContext userContext) {
        return new CassandraEventRecorder(eventApisConfiguration
                .getTableNameForEvents("payment"), cassandraSession, operationContext, userContext, new ObjectMapper());
    }

    @Bean
    public EventRepository paymentEventRepository(EventRecorder paymentEventRecpayment, IOperationRepository operationRepository) {
        return new CompositeRepositoryImpl(paymentEventRecpayment, new ObjectMapper(), operationRepository);
    }

    @Bean
    public DataMigrationService dataMigrationService(EventRecorder paymentEventRecpayment, ViewQuery<Payment> paymentViewQuery, PaymentRepository paymentRepository) {
        return new DataMigrationService(paymentEventRecpayment, paymentViewQuery, paymentRepository);
    }

}

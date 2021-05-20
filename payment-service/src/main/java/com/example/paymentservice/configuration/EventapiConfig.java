package com.example.paymentservice.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.splitet.core.api.EventRepository;
import io.splitet.core.api.IUserContext;
import io.splitet.core.api.RollbackSpec;
import io.splitet.core.api.ViewQuery;
import io.splitet.core.cassandra.CassandraEventRecorder;
import io.splitet.core.cassandra.CassandraSession;
import io.splitet.core.cassandra.CassandraViewQuery;
import io.splitet.core.common.EventRecorder;
import io.splitet.core.common.OperationContext;
import io.splitet.core.core.CompositeRepositoryImpl;
import io.splitet.core.kafka.IOperationRepository;
import io.splitet.core.spring.configuration.DataMigrationService;
import io.splitet.core.spring.configuration.EventApisConfiguration;
import io.splitet.core.view.AggregateListener;
import io.splitet.core.view.EntityFunctionSpec;
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

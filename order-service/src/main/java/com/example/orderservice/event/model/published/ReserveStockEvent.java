package com.example.orderservice.event.model.published;

import com.fasterxml.jackson.annotation.JsonView;
import io.splitet.core.api.Views;
import io.splitet.core.common.EventType;
import io.splitet.core.common.PublishedEvent;
import com.example.orderservice.entity.PaymentInformation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReserveStockEvent extends PublishedEvent {

    private String stockId;

    private long numberOfItemsSold;

    @JsonView(Views.RecordedOnly.class)
    private PaymentInformation paymentInformation;

    private EventType eventType = EventType.OP_START;

}

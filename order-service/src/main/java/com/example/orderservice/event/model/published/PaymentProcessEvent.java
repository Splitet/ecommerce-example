package com.example.orderservice.event.model.published;

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
public class PaymentProcessEvent extends PublishedEvent {

    private String orderId;

    private int reservedStockVersion;

    private PaymentInformation paymentInformation;

    private EventType eventType = EventType.EVENT;
}

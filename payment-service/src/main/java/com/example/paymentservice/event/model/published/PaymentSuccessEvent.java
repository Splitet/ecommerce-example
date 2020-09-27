package com.example.paymentservice.event.model.published;

import com.fasterxml.jackson.annotation.JsonView;
import com.kloia.eventapis.api.Views;
import com.kloia.eventapis.common.EventType;
import com.kloia.eventapis.common.PublishedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentSuccessEvent extends PublishedEvent {

    private String orderId;

    private String paymentAddress;

    private float amount;

    @JsonView(Views.RecordedOnly.class)
    private String cardInformation;

    private EventType eventType = EventType.EVENT;
}

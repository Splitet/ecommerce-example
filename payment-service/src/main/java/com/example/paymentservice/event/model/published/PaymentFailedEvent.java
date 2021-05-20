package com.example.paymentservice.event.model.published;

import com.fasterxml.jackson.annotation.JsonView;
import io.splitet.core.api.Views;
import io.splitet.core.common.EventType;
import io.splitet.core.common.PublishedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentFailedEvent extends PublishedEvent {

    private float amount;

    private String orderId;

    private String paymentAddress;

    @JsonView(Views.RecordedOnly.class)
    private String cardInformation;

    private EventType eventType = EventType.OP_FAIL;
}

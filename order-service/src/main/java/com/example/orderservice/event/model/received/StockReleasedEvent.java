package com.example.orderservice.event.model.received;

import io.splitet.core.common.ReceivedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StockReleasedEvent extends ReceivedEvent {

    private String orderId;

    private long numberOfItemsReleased;
}

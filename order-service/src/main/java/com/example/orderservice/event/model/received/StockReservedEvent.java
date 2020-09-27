package com.example.orderservice.event.model.received;

import com.kloia.eventapis.common.ReceivedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StockReservedEvent extends ReceivedEvent {

    private String stockId;

    private String orderId;

    private long numberOfItemsSold;
}

package com.example.stockservice.event.model.received;

import com.kloia.eventapis.common.ReceivedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReserveStockEvent extends ReceivedEvent {

    private String stockId;

    private long numberOfItemsSold;

}

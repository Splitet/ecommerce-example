package com.example.stockservice.event.model.received;

import com.kloia.eventapis.common.ReceivedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WaitingStockReleaseEvent extends ReceivedEvent {

    private String stockId;

    private int reservedStockVersion;
}

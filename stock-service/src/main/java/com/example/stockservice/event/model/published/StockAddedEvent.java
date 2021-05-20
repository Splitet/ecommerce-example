package com.example.stockservice.event.model.published;

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
public class StockAddedEvent extends PublishedEvent {
    private long addedStock;

    private EventType eventType = EventType.OP_SINGLE;
}

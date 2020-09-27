package com.example.stockservice.event.model.published;

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
public class StockCreatedEvent extends PublishedEvent {

    private String stockName;

    private long remainingStock;

    private EventType eventType = EventType.OP_SINGLE;
}

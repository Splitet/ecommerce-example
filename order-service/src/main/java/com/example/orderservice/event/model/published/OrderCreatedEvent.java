package com.example.orderservice.event.model.published;

import com.kloia.eventapis.common.EventType;
import com.kloia.eventapis.common.PublishedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreatedEvent extends PublishedEvent {

    private String stockId;

    private int orderAmount;

    private String description;

    private EventType eventType = EventType.OP_SINGLE;
}

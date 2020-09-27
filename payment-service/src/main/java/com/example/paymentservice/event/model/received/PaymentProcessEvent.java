package com.example.paymentservice.event.model.received;

import com.kloia.eventapis.common.ReceivedEvent;
import com.example.paymentservice.entity.PaymentInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessEvent extends ReceivedEvent {

    private PaymentInformation paymentInformation;
}

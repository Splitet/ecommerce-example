package com.example.orderservice.client;

import com.kloia.eventapis.api.CommandDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnPaymentDto implements CommandDto {
    private String orderId;
}

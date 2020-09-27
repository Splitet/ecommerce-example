package com.example.orderservice.resource.model;

import com.example.orderservice.entity.PaymentInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessOrderDto {

    private PaymentInformation paymentInformation;

}

package com.example.paymentservice.util.mapper;

import com.example.paymentservice.command.model.ReturnPaymentCommand;
import com.example.paymentservice.resource.model.ReturnPaymentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

	ReturnPaymentCommand toCommand(String paymentId, ReturnPaymentDto dto);
}

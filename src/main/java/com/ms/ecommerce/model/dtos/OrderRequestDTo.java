package com.ms.ecommerce.model.dtos;

import com.ms.ecommerce.model.enums.OrderPayment;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderRequestDTo(

        @NotNull(message = "The product id is necessary")
        UUID productId,

        @NotNull(message = "The order quantity is necessary")
        Integer quantity,

        @NotNull(message = "The order payment is necessary")
        OrderPayment payment
) {
}

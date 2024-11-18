package com.ms.ecommerce.model.dtos;

import com.ms.ecommerce.model.enums.OrderPayment;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record OrderRequestDTo(
        @NotBlank(message = "The product id is necessary")
        UUID productId,

        @NotBlank(message = "The order quantity is necessary")
        Integer quantity,

        @NotBlank(message = "The order payment is necessary")
        OrderPayment payment
) {
}

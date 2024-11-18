package com.ms.ecommerce.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "The product name is necessary")
        String name,

        String description,

        @NotNull(message = "The product price is necessary")
        BigDecimal price,

        @NotNull(message = "The product quantity is necessary")
        Integer quantity
) {
}


package com.ms.ecommerce.model.dtos;

public record CustomError(
        String field,
        String error
) {
}

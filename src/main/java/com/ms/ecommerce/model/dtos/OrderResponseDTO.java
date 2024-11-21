package com.ms.ecommerce.model.dtos;

import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.model.enums.OrderPayment;
import com.ms.ecommerce.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponseDTO(
        UUID id,
        String productName,
        Integer quantity,
        OrderPayment payment,
        OrderStatus status,
        LocalDateTime orderDate
) {
}

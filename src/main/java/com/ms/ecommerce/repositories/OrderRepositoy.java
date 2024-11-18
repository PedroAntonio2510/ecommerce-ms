package com.ms.ecommerce.repositories;

import com.ms.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepositoy extends JpaRepository<Order, UUID> {
}

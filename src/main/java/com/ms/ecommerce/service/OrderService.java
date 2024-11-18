package com.ms.ecommerce.service;

import com.ms.ecommerce.model.Order;
import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.model.enums.OrderStatus;
import com.ms.ecommerce.repositories.OrderRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepositoy repositoy;

    @Autowired
    private ProductService productService;

    public Order saveOrder(Order order){
        Optional<Product> product = productService.getProductById(order.getProduct().getId());

        if (!product.isPresent()) {
            throw new IllegalArgumentException("Product not found");
        }

        if (order.getQuantity() > product.get().getQuantity()){
            throw new RuntimeException("Insufficient stock");
        }

        order.setStatus(OrderStatus.PROCESSED);
        return repositoy.save(order);
    }
}

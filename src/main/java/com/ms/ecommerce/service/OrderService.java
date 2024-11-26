package com.ms.ecommerce.service;

import com.ms.ecommerce.model.Order;
import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.model.enums.OrderStatus;
import com.ms.ecommerce.repositories.OrderRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepositoy repositoy;
    private final ProductService productService;


    public Order saveOrder(Order order) {

        Optional<Product> productOptional = productService.getProductById(order.getProduct().getId());

        if (!productOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOptional.get();

        if (order.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setQuantity(product.getQuantity() - order.getQuantity());
        productService.saveProduct(product);
        order.setStatus(OrderStatus.PROCESSED);
        return repositoy.save(order);
    }

    public List<Order> getOrders() {
        return repositoy.findAll();
    }

    public Optional<Order> getOrderById(UUID id){
        return repositoy.findById(id);
    }

    public Order updateOrder(Order order) {
        if (order.getId() == null) {
            throw new IllegalArgumentException("You must provide a valid id from an order");
        }
        return repositoy.save(order);
    }
}

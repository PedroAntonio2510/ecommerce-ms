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
    private final RabbitmqNotificationService rabbitmqNotificationService;

    public Order saveOrder(Order response) {

        Optional<Product> productOptional = productService.getProductById(response.getProduct().getId());

        if (!productOptional.isPresent()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOptional.get();

        if (response.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setQuantity(product.getQuantity() - response.getQuantity());
        productService.saveProduct(product);
        response.setStatus(OrderStatus.PENDING);

        Order savedOrder = repositoy.save(response);

        if (savedOrder.getId() == null){
            throw new RuntimeException("ID is null after saving");
        }

        rabbitmqNotificationService.notificate(savedOrder, "order-notification.ex");
        return savedOrder;
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

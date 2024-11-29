package com.ms.ecommerce.controller;

import com.ms.ecommerce.model.Order;
import com.ms.ecommerce.model.UserModel;
import com.ms.ecommerce.model.dtos.OrderRequestDTo;
import com.ms.ecommerce.model.dtos.OrderResponseDTO;
import com.ms.ecommerce.model.mapper.OrderMapper;
import com.ms.ecommerce.security.SecurityService;
import com.ms.ecommerce.service.OrderService;
import com.ms.ecommerce.service.RabbitmqNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements GenericController{

    private final OrderService service;
    private final OrderMapper mapper;
    private final SecurityService securityService;
    private final RabbitmqNotificationService rabbitmqNotificationService;


    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'CLIENT')")
    public ResponseEntity<?> save(@RequestBody @Valid OrderRequestDTo dto){
        UserModel userModel = securityService.getUserLogged();
        Order order = mapper.toEntity(dto);
        order.setUser(userModel);
        service.saveOrder(order);
        URI uri = headerLocation(order.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Order order) {
        return service.getOrderById(UUID.fromString(id))
                .map(existingOrder -> {
                    existingOrder.setStatus(order.getStatus());
                    service.updateOrder(existingOrder);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'CLIENT')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = service.getOrders();
        List<OrderResponseDTO> orderResponse = orders
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderResponse);
    }
}

package com.ms.ecommerce.controller;

import com.ms.ecommerce.model.Order;
import com.ms.ecommerce.model.dtos.OrderRequestDTo;
import com.ms.ecommerce.model.dtos.OrderResponseDTO;
import com.ms.ecommerce.model.mapper.OrderMapper;
import com.ms.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController implements GenericController{

    @Autowired
    private OrderService service;

    @Autowired
    private OrderMapper mapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid OrderRequestDTo dto){
        Order order = mapper.toEntity(dto);
        URI uri = headerLocation(order.getId());
        service.saveOrder(order);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<Order> orders = service.getOrders();
        List<OrderResponseDTO> orderResponse = orders
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderResponse);
    }
}

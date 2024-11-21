package com.ms.ecommerce.model.mapper;

import com.ms.ecommerce.model.Order;
import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.model.dtos.OrderRequestDTo;
import com.ms.ecommerce.model.dtos.OrderResponseDTO;
import com.ms.ecommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public abstract class OrderMapper {

    @Autowired
    ProductRepository productRepository;

    @Mapping(target = "product", expression = "java( productRepository.findById(dto.productId()).orElse(null) )")
    public abstract Order toEntity(OrderRequestDTo dto);

    public abstract OrderResponseDTO toDTO(Order order);

}

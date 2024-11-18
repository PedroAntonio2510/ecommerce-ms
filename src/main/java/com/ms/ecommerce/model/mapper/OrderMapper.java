package com.ms.ecommerce.model.mapper;

import com.ms.ecommerce.model.Order;
import com.ms.ecommerce.model.dtos.OrderRequestDTo;
import com.ms.ecommerce.model.dtos.OrderResponseDTO;
import com.ms.ecommerce.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper()
public abstract class OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Autowired
    ProductRepository productRepository;

    @Mapping(target = "product", expression = "java( productRepository.findById(dto.productId()).orElse(null) )")
    abstract Order toEntity(OrderRequestDTo dto);

    abstract OrderResponseDTO toDTO(Order order);
}

package com.ms.ecommerce.model.mapper;

import com.ms.ecommerce.model.Order;
import com.ms.ecommerce.model.dtos.OrderRequestDTo;
import com.ms.ecommerce.model.dtos.OrderResponseDTO;
import com.ms.ecommerce.repositories.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public abstract class OrderMapper {

    @Autowired
    ProductRepository productRepository;

    @Mapping(target = "product", expression = "java( productRepository.findById(dto.productId()).orElse(null) )")
    public abstract Order toEntity(OrderRequestDTo dto);

    @Mapping(target = "productName", source = "product.name")
    public abstract OrderResponseDTO toDTO(Order order);

}

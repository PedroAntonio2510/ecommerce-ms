package com.ms.ecommerce.model.mapper;

import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.model.dtos.ProductRequestDTO;
import com.ms.ecommerce.model.dtos.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDTO(Product product);
}

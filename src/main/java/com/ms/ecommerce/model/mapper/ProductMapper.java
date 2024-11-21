package com.ms.ecommerce.model.mapper;

import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.model.dtos.ProductRequestDTO;
import com.ms.ecommerce.model.dtos.ProductResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDTO(Product product);
}

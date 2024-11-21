package com.ms.ecommerce.model.mapper;

import com.ms.ecommerce.model.UserModel;
import com.ms.ecommerce.model.dtos.UserModelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    UserModel toEntity(UserModelDTO dto);

    UserModelDTO toDTO(UserModel model);
}

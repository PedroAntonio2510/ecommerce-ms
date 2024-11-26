package com.ms.ecommerce.validator;

import com.ms.ecommerce.exceptions.ProductDuplicateException;
import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.model.UserModel;
import com.ms.ecommerce.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserModelValidator {

    @Autowired
    private UserModelRepository repository;

    public void validate(UserModel userModel){
        if (existsUserModel(userModel)) {
            throw new ProductDuplicateException("Product already registered");
        }
    }

    private boolean existsUserModel(UserModel userModel) {
        Optional<UserModel> userOptional = repository
                .findByCpf(userModel.getCpf());
        if (userModel.getId() == null) {
            return userOptional.isPresent();
        }
        return userOptional
                .map(UserModel::getId)
                .stream()
                .anyMatch(id -> !id.equals(userModel.getId()));

    }
}
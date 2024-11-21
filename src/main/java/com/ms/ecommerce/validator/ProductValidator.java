package com.ms.ecommerce.validator;

import com.ms.ecommerce.exceptions.ProductDuplicateException;
import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductValidator {

    @Autowired
    private ProductRepository repository;

    public void validate(Product product){
        if (existsProduct(product)) {
            throw new ProductDuplicateException("Product already registered");
        }
    }

    private boolean existsProduct(Product product) {
        Optional<Product> productOptional = repository
                .findProductByNameEqualsIgnoreCase(product.getName());
        if (product.getId() == null) {
            return productOptional.isPresent();
        }
        return productOptional
                .map(Product::getId)
                .stream()
                .anyMatch(id -> !id.equals(product.getId()));

    }
}
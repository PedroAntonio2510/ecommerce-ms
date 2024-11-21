package com.ms.ecommerce.service;

import com.ms.ecommerce.model.Product;
import com.ms.ecommerce.repositories.ProductRepository;
import com.ms.ecommerce.repositories.specs.ProductSpecs;
import com.ms.ecommerce.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductValidator validator;

    public Product saveProduct(Product product){
        validator.validate(product);
        return repository.save(product);
    }

    public List<Product> getProducts(){
        return repository.findAll();
    }

    public Optional<Product> getProductById(UUID id){
        return repository.findById(id);
    }

    public void deleteProduct(Product product) {
        repository.delete(product);
    }

    public List<Product> searchProducts(String name,
                                        BigDecimal price) {
        Specification<Product> specs = Specification.where(
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction()
        );
        if (name != null) {
            specs = specs.and(ProductSpecs.nameLike(name));
        }
        if (price != null){
            specs = specs.and(ProductSpecs.priceLessThan(price));
        }
        return repository.findAll(specs);
    }
}

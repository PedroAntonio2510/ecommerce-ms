package com.ms.ecommerce.repositories.specs;

import com.ms.ecommerce.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecs {

    public static Specification<Product> nameLike(String name) {
        return (root, query, cb) -> cb.like(
                cb.upper(root.get("name")), "%" + name.toUpperCase() + "%"
        );
    }

    public static Specification<Product> priceLessThan(BigDecimal price) {
        return (root, query, cb) -> cb.lessThan(
                root.get("price"), price);
    }

}

package com.ms.ecommerce.exceptions;

public class ProductDuplicateException extends RuntimeException{

    public ProductDuplicateException(String message) {
        super(message);
    }
}

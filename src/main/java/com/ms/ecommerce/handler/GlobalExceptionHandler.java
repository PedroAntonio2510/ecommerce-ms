package com.ms.ecommerce.handler;

import com.ms.ecommerce.exceptions.InsufficientStockException;
import com.ms.ecommerce.exceptions.ProductDuplicateException;
import com.ms.ecommerce.model.dtos.CustomAnswerError;
import com.ms.ecommerce.model.dtos.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomAnswerError handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrorList = e.getFieldErrors();
        List<CustomError> errorList = fieldErrorList
                .stream()
                .map(fe -> new CustomError(
                        fe.getField(),
                        fe.getDefaultMessage()
                )).collect(Collectors.toList());
        return new CustomAnswerError(
                "Validation error",
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                errorList);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CustomAnswerError handleAcessDeniedException(AccessDeniedException e){
        return new CustomAnswerError("Acess denied", HttpStatus.FORBIDDEN.value(), List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomAnswerError handleErrors(RuntimeException e){
        System.out.printf(e.getMessage());
        return new CustomAnswerError(
                "An error ocurred, get contact with the support",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of()
        );
    }

    @ExceptionHandler(ProductDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomAnswerError handleProductDuplicateException(ProductDuplicateException e){
        return CustomAnswerError.conflict(e.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public CustomAnswerError handleInsufficientStockException(InsufficientStockException e){
        return CustomAnswerError.notAllowed(e.getMessage());
    }
}

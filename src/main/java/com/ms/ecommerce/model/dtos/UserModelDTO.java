package com.ms.ecommerce.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record UserModelDTO(
        @NotBlank(message = "You musta have a login name")
        String login,

        @NotBlank(message = "You must have a password")
        String password,

        @CPF(message = "The cpf must be valid")
        @NotBlank(message = "CPF is required")
        String cpf,

        @Email
        @NotBlank(message = "Email is required")
        String email,

        List<String> roles
) {
}

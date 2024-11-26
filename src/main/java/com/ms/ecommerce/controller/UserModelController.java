package com.ms.ecommerce.controller;

import com.ms.ecommerce.model.dtos.UserModelDTO;
import com.ms.ecommerce.model.mapper.UserModelMapper;
import com.ms.ecommerce.service.UserModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserModelController {

    private final UserModelService userModelService;
    private final UserModelMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid UserModelDTO dto) {
        var user = mapper.toEntity(dto);
        userModelService.saveUser(user);
    }
}

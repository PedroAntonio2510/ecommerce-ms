package com.ms.ecommerce.service;

import com.ms.ecommerce.model.UserModel;
import com.ms.ecommerce.repositories.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserModelService {

    private final UserModelRepository repository;
    private final PasswordEncoder encoder;

    public void saveUser(UserModel user){
        var password = user.getPassword();
        user.setPassword(encoder.encode(password));
        repository.save(user);
    }

    public UserModel getByLogin(String login){
        return repository.findByLogin(login);
    }

    public UserModel getByEmail(String email){
        return repository.findByEmail(email);
    }
}

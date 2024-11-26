package com.ms.ecommerce.repositories;

import com.ms.ecommerce.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserModelRepository extends JpaRepository<UserModel, UUID> {

    UserModel findByLogin(String login);

    UserModel findByEmail(String email);
}

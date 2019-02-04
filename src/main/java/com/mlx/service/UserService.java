package com.mlx.service;

import com.mlx.domain.entities.User;
import com.mlx.domain.models.binding.UserRegisterBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(UserRegisterBindingModel bindingModel);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

    User findByUsername(String username);

    List<User> findAll();
}

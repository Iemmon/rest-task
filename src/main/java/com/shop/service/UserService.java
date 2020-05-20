package com.shop.service;

import com.shop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    User addNewUser(User customer);

    void updateExistingUser(User customer);

    void deleteUserById(Integer id);

    Optional<User> getUserById(Integer id);
}

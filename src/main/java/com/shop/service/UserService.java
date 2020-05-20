package com.shop.service;

import com.shop.entity.User;

import java.util.Optional;

public interface UserService {

    User addNewUser(User customer);

    void updateExistingUser(User customer);

    void deleteUserById(Integer id);

    Optional<User> getUserById(Integer id);
}

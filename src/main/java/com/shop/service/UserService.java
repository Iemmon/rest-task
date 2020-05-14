package com.shop.service;

import com.shop.entity.User;

import java.util.Optional;

public interface UserService {

    User addNewCustomer(User customer);

    void updateExistingCustomer(User customer);

    void deleteCustomerById(Integer id);

    Optional<User> getCustomerById(Integer id);
}

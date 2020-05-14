package com.shop.service;

import com.shop.entity.User;

public interface UserService {

    User addNewCustomer(User customer);

    User updateExistingCustomer(User customer);

    void deleteCustomerById(Integer id);

    User getCustomerById(Integer id);
}

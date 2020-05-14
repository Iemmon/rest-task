package com.shop.service.impl;

import com.shop.entity.User;
import com.shop.exception.UserDoesNotExistException;
import com.shop.repository.UserRepository;
import com.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User addNewCustomer(User customer) {
        return userRepository.save(customer);
    }

    @Override
    public User updateExistingCustomer(User customer) {
        return userRepository.findById(customer.getId()).orElseThrow(UserDoesNotExistException::new);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getCustomerById(Integer id) {
        return userRepository.findById(id).orElseThrow(UserDoesNotExistException::new);
    }
}

package com.shop.service.impl;

import com.shop.entity.User;
import com.shop.repository.UserRepository;
import com.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User addNewCustomer(User customer) {
        return userRepository.save(customer);
    }

    @Override
    public void updateExistingCustomer(User customer) {
        userRepository.findById(customer.getId());
    }

    @Override
    public void deleteCustomerById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getCustomerById(Integer id) {
        return userRepository.findById(id);
    }
}

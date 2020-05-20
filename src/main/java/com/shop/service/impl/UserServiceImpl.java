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
    public User addNewUser(User customer) {
        customer.getCart().forEach(product -> product.setCustomer(customer));
        return userRepository.save(customer);
    }

    @Override
    public void updateExistingUser(User customer) {
        userRepository.save(customer);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }
}

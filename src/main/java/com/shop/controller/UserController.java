package com.shop.controller;

import com.shop.entity.User;
import com.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public User addCustomer(@RequestBody User customer) {
        return userService.addNewCustomer(customer);
    }

    @PutMapping("/update")
    public User updateCustomer(@RequestBody User customer) {
        return userService.updateExistingCustomer(customer);
    }

    @DeleteMapping("/delete")
    public void deleteCustomer(Integer id) {
        userService.deleteCustomerById(id);
    }

    @GetMapping("/get")
    public User getCustomer(Integer id) {
        return userService.getCustomerById(id);
    }
}

package com.shop.controller;

import com.shop.entity.User;
import com.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> addCustomer(@RequestBody User customer) {
        User resultUser = userService.addNewCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultUser);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateCustomer(@RequestBody User customer) {
        Integer id = customer.getId();
        HttpStatus status = HttpStatus.CREATED;
        if (id != null && userService.getCustomerById(id).isPresent()) {
            status = HttpStatus.OK;
        }
        userService.updateExistingCustomer(customer);
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCustomer(Integer id) {
        if (!userService.getCustomerById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        userService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/get")
    public ResponseEntity<User> getCustomer(Integer id) {
        Optional<User> resultUser = userService.getCustomerById(id);
        return resultUser.map(user
                -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}

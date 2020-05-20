package com.shop.controller;

import com.shop.entity.User;
import com.shop.exceptionhandler.ResourceNotFoundException;
import com.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User resultUser = userService.addNewUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultUser);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        Integer id = user.getId();
        HttpStatus status = HttpStatus.CREATED;
        if (id != null && userService.getUserById(id).isPresent()) {
            status = HttpStatus.OK;
        }
        userService.updateExistingUser(user);
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.getUserById(id).orElseThrow(ResourceNotFoundException::new);
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        User resultUser = userService.getUserById(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(resultUser);
    }
}

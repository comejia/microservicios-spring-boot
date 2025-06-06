package com.comejia.msvc.users.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.comejia.msvc.users.entities.User;
import com.comejia.msvc.users.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        this.logger.info("UserController - Obteniendo usuario con id: {}", id);
        return this.userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        this.logger.info("UserController - Obteniendo usuario con username: {}", username);
        return this.userService.findByUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        this.logger.info("UserController - Obteniendo usuarios");;
        List<User> users = this.userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        this.logger.info("UserController - Creando usuario: {}", user);
        User newUser = this.userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        this.logger.info("UserController - Actualizando usuario: {}", user);
        return this.userService.update(user, id)
            .map(updatedUser -> ResponseEntity.status(HttpStatus.CREATED).body(updatedUser))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.logger.info("UserController - Eliminando usuario con id: {}", id);
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


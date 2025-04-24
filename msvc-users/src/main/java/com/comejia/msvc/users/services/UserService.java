package com.comejia.msvc.users.services;

import java.util.List;
import java.util.Optional;

import com.comejia.msvc.users.entities.User;

public interface UserService {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void delete(Long id);
}

package com.comejia.msvc.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.comejia.msvc.users.entities.Role;
import com.comejia.msvc.users.entities.User;
import com.comejia.msvc.users.repositories.RoleRepository;
import com.comejia.msvc.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = this.roleRepository.findByName("ROLE_USER");

        roleOptional.ifPresent(roles::add);

        if (user.isAdmin()) {
            Optional<Role> roleAdminOptional = this.roleRepository.findByName("ROLE_ADMIN");
            roleAdminOptional.ifPresent(roles::add);
        }

        return roles;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setRoles(getRoles(user));
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(User user, Long id) {
        Optional<User> userOptional = this.findById(id);

        return userOptional.map(userDb -> {
                userDb.setUsername(user.getUsername());
                userDb.setEmail(user.getEmail());
                userDb.setEnabled(user.isEnabled() != null ? user.isEnabled() : true);
                userDb.setRoles(getRoles(user));
                userDb.setAdmin(user.isAdmin());

                return Optional.of(this.userRepository.save(userDb));
            })
            .orElse(Optional.empty());        
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }
}


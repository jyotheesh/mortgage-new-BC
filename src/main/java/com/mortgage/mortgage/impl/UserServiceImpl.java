package com.mortgage.mortgage.impl;


import com.mortgage.mortgage.entity.User;
import com.mortgage.mortgage.repository.UserRepository;
import com.mortgage.mortgage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByName(String name) {
        User user = null;
        if (name == null) {
            return null;
        }
        final User byEmailAndState = userRepository.findByName(name);
        // final Company company = byEmailAndState.getCompany();
       /* if (company.isActive()) {
            user = byEmailAndState;
        }*/
        return byEmailAndState;
    }

    @Override
    public void create(final User user) {
        // LOG.info("User creation requested '{}'", user);
        /* validate user */
        validate(user);
        //map role
        String role = user.getRole();
        if (role == null) {
            role = "ROLE_USER";
        }
        //ususerer.setRole(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User createdUser = userRepository.saveAndFlush(user);
    }

    private void validate(final User user) {
        if (user == null) {
            throw new RuntimeException("NULL user cannot be saved");
        }

        final String email = user.getEmail();
        if (email == null || email.length() < 1) {
            // LOG.error("Email cannot be blank", user);
            throw new RuntimeException("Email cannot be blank");
        }
        final User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            //LOG.error("User with email '{}' already exists.");
            throw new RuntimeException("User with email '" + user.getEmail() + "' already exists.");
        }

    }

}

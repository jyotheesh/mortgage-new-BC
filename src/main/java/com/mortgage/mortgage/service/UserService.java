package com.mortgage.mortgage.service;


import com.mortgage.mortgage.entity.User;
//import com.valoya.login.Entity.User;

public interface UserService {
    User findByName(String email);

    void create(User user);

}

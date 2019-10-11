package com.mortgage.mortgage.util;

import com.mortgage.mortgage.dto.UserDto;
import com.mortgage.mortgage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PVOUtil {
    @Autowired
    private static BCryptPasswordEncoder bCryptPasswordEncoder;

   /* public static final User createNew(final UserDto customerDto) {
        if (customerDto == null) {
            return null;
        }
        User customer = new User();
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
       // customerDto.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));

        customer.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
        customer.setRole(customerDto.getRole());

        return customer;
    }*/


}

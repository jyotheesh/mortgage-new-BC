package com.mortgage.mortgage.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
@Getter
@Setter
public class JwtResponse {
    private String token;
    private Integer user_id;

    public JwtResponse(String accessToken, Integer user_id) {
        this.token = accessToken;
        this.user_id=user_id;

    }



}
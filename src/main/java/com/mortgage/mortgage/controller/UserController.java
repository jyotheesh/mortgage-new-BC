
package com.mortgage.mortgage.controller;

import com.mortgage.mortgage.Constants.SecurityConstants;
import com.mortgage.mortgage.dto.JwtResponse;
import com.mortgage.mortgage.entity.User;
import com.mortgage.mortgage.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;


@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> apply(@RequestBody User user) {
        //customer.setPassword(bCryptPasswordEncoder.encode(userPVO.getPassword()));
        // User user = PVOUtil.createNew(userPVO);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        LOGGER.info("User created" + user.getName());

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(), new ArrayList<>()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = Jwts.builder()
                .setSubject(user.getName())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();


        //String jwt = JwtGenerator.generate(user);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        LOGGER.info("User has been logged in token has been returned");
       User user1= userRepository.findByName(user.getName());
        // String role=  ((org.springframework.security.core.userdetails.User) User.getPrincipal()).getAuthorities().toString();
        return ResponseEntity.ok(new JwtResponse(SecurityConstants.TOKEN_PREFIX + token, user1.getUserId()));

    }
}

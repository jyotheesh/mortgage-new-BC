package com.mortgage.mortgage.impl;



import com.mortgage.mortgage.entity.User;
import com.mortgage.mortgage.repository.UserRepository;
import com.mortgage.mortgage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String arg0)
            throws UsernameNotFoundException {

        final User loggedUser = userService.findByName(arg0);
       Optional<User> user=Optional.ofNullable(loggedUser);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("No user found with name " + loggedUser);
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + loggedUser.getRole());

        return new org.springframework.security.core.userdetails.User(
                loggedUser.getName(), loggedUser.getPassword(), grantedAuthorities);
    }
}

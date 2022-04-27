package com.example.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final String USERNAME = "ozdemirsahin";
    private final String ENCRYPTED_PASSWORD = "$2a$12$cimwoojqe1f5UXdTmPPNguDSEWaihMEJyyV01tQbZXmsp08Rrcb.O"; // 12345

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (USERNAME.equals(username)) {
            return new User(USERNAME, ENCRYPTED_PASSWORD, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}

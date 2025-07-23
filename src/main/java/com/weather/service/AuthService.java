package com.weather.service;

import com.weather.repos.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService implements ReactiveUserDetailsService {

    @Autowired
    private AuthRepo repo;


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return repo.findByUsername(username)
                .map(user ->
                        User.withUsername(user.getUsername())
                                .password("{noop}" +user.getPassword())
                                .build());
    }
}

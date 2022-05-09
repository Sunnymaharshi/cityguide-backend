package com.cityguide.backend.jwt;

import java.util.ArrayList;
import java.util.Optional;

import com.cityguide.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.cityguide.backend.entities.User> check = repo.findById(username);
        if (check.isEmpty()) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("User not found with username: " + username);
        } else {
            com.cityguide.backend.entities.User user=repo.findById(username).get();
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    new ArrayList<>());
        }
    }
}

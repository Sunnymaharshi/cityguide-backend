package com.cityguide.backend.services;

import com.cityguide.backend.entities.User;
import com.cityguide.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> signup(User user)
    {

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.ACCEPTED);
    }
}

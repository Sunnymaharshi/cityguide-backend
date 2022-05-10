package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.User;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.UserRepository;
import com.cityguide.backend.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public ResponseEntity<String> signup(@RequestBody User user)
    {
        return userServices.signup(user);
    }
    @RequestMapping(value = "/ep1",method = RequestMethod.GET)
    public String ep1(@RequestHeader("Authorization") String requestTokenHeader)
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        return user1.getName();
    }
}

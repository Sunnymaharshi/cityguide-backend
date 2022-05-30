package com.cityguide.backend.jwt;

import java.util.Objects;
import java.util.Optional;


import com.cityguide.backend.entities.Loginobject;
import com.cityguide.backend.entities.User;
import com.cityguide.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder bcryptEncoder;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Loginobject loginobject) throws Exception {
        Optional<User> check = userRepository.findById(loginobject.getUsername());
        if (check.isEmpty()) {
            return new ResponseEntity<>("Username does not exist!", HttpStatus.OK);
        } else if (check.isPresent()) {
            User user = userRepository.findById(loginobject.getUsername()).get();
            String password=loginobject.getPassword();
            if (user.getPassword().equals(bcryptEncoder.encode(password))) {
                authenticate(loginobject.getUsername(), loginobject.getPassword());

                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(loginobject.getUsername());

                final String token = jwtTokenUtil.generateToken(userDetails);


                return ResponseEntity.ok(new JwtResponse(token));
            }
            else {
                return new ResponseEntity<>("Wrong Password!", HttpStatus.OK);
            }
        }
        else return new ResponseEntity<>("Something Went Wrong",HttpStatus.METHOD_FAILURE);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
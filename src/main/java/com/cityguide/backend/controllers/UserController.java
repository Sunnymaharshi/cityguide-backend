package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.*;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import com.cityguide.backend.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;



@RestController
public class UserController {


    @Autowired
    UserServices userServices;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;



    @Autowired
    CityRepository cityRepository;



    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signup(@RequestBody User user) {
        return userServices.signup(user);
    }



    //<---------------------------------------------------------User operations for Cities,Rest,Attr--------------------------------------------------->

    @RequestMapping(value = "/getallcities", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getcities() {
        return userServices.getcities();
    }

    @RequestMapping(value = "/getcitynames", method = RequestMethod.GET)
    public ResponseEntity<?> getcitynames() {
        return userServices.getCityNames();
    }

    @RequestMapping(value = "/getcity/{city}", method = RequestMethod.GET)
    public ResponseEntity<City> getcities(@PathVariable("city") String city) {
        return userServices.getcity(city);
    }

    //Getting Restaurants Api
    @RequestMapping(value = "/getrest/{city}", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> getRestaurants(@PathVariable("city") String city) {
        return userServices.getRestaurants(city);
    }

    //Getting Attractions Api
    @RequestMapping(value = "/getattr/{city}", method = RequestMethod.GET)
    public ResponseEntity<List<Attractions>> getattr(@PathVariable("city") String city) {
        return userServices.getattr(city);
    }

    @RequestMapping(value = "/city/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> getmcity(@PathVariable("city") String city) {
        return userServices.getmcity(city);
    }

    //<---------------------------------------------------------User Details------------------------------------------------------------------------------>
    //getting user details Api
    @RequestMapping(value = "/getuserdetails", method = RequestMethod.GET)
    public ResponseEntity<?> getuserdetails(@RequestHeader("Authorization") String requestToken) {
        return userServices.getuserdetails(requestToken);
    }

    //get user username and password
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResponseEntity<?> getusername(@RequestHeader("Authorization") String requestToken) {
        return userServices.getusername(requestToken);
    }

    //-------------------------------------------------------Upvote,DownVote----------------------------------------------------------------------------->
    @RequestMapping(value = "/upvote/{ansid}", method = RequestMethod.PUT)
    public ResponseEntity<?> upvote(@RequestHeader("Authorization") String requestToken, @PathVariable("ansid") int ansid) {
        return userServices.upvote(requestToken, ansid);
    }

    @RequestMapping(value = "/downvote/{ansid}", method = RequestMethod.PUT)
    public ResponseEntity<?> downvote(@RequestHeader("Authorization") String requestToken, @PathVariable("ansid") int ansid) {
        return userServices.downvote(requestToken, ansid);
    }

    @RequestMapping(value = "/checkvote/{ansid}", method = RequestMethod.GET)
    public ResponseEntity<?> check(@RequestHeader("Authorization") String requestToken, @PathVariable("ansid") int ansid) {
        return userServices.check(requestToken, ansid);
    }


    //----------------------------------------------------------MetroMap,BusMap--------------------------------------------------------------------------->



    @RequestMapping(value = "/getmetro/{city}",method = RequestMethod.GET)
    public ResponseEntity<?> getmetro(@PathVariable("city") String city)
    {
        try {

            return new ResponseEntity<>(cityRepository.findById(city).get().getMetroMapList(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/getbus/{city}",method = RequestMethod.GET)
    public ResponseEntity<?> getbus(@PathVariable("city") String city)
    {
        try {

            return new ResponseEntity<>(cityRepository.findById(city).get().getBusMapList(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }

    }

}




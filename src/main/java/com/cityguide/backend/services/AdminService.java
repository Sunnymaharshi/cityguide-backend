package com.cityguide.backend.services;

import com.cityguide.backend.entities.Attractions;
import com.cityguide.backend.entities.City;
import com.cityguide.backend.entities.Restaurant;
import com.cityguide.backend.entities.User;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.AttractionsRepository;
import com.cityguide.backend.repositories.CityRepository;
import com.cityguide.backend.repositories.RestaurantRepository;
import com.cityguide.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AttractionsRepository attractionsRepository;

    @Autowired
    RestaurantRepository restaurantRepository;


    //CRUD for Cities

    public ResponseEntity<?> addcity(String requestTokenHeader,City city)//add city
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        if(user1.getRole().equalsIgnoreCase("Admin"))
        {
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }
    public ResponseEntity<?> updatecity(String requestTokenHeader,City city)//update city
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        if(user1.getRole().equalsIgnoreCase("Admin"))
        {
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }
    public ResponseEntity<?> deletecity(String requestTokenHeader,String city)//delete city
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        if(user1.getRole().equalsIgnoreCase("Admin"))
        {
            cityRepository.deleteById(city);
            return new ResponseEntity<>("Deleted!", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }



    //CRUD for attractions

    public ResponseEntity<?> addattr(String requestTokenHeader, Attractions attraction){
    return new ResponseEntity<>(attractionsRepository.save(attraction), HttpStatus.ACCEPTED);}//add attraction

    //CRUD for Restaurants
    //Adding Restaurant
    public ResponseEntity<?> addRestaurant(String requestTokenHeader, Restaurant restaurant){
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        if(user1.getRole().equalsIgnoreCase("Admin")){
            return new ResponseEntity<>(restaurantRepository.save(restaurant),HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

   //updating Restaurant
    public ResponseEntity<?> updateRestaurant(String requestTokenHeader, Restaurant restaurant)//update restaurant
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        if(user1.getRole().equalsIgnoreCase("Admin"))
        {
            return new ResponseEntity<>(restaurantRepository.save(restaurant), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }
    //Delete Restaurant
    public ResponseEntity<?> deleteRestaurant(String requestTokenHeader,int res_id)//delete restaurant

    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        if(user1.getRole().equalsIgnoreCase("Admin"))
        {



            restaurantRepository.deleteById(res_id);
            return new ResponseEntity<>("Deleted!", HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

}



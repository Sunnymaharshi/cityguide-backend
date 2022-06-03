package com.cityguide.backend.services;

import com.cityguide.backend.entities.*;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import com.cityguide.backend.types.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    BusRepository busRepository;


    //<------------------------------------------------------------------CRUD for Cities--------------------------------------------------------------------------->

    public ResponseEntity<?> addcity(String requestTokenHeader, City city)//add city
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();
        System.out.println(user1.getRole() + Role.ADMIN);
        if (user1.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> updatecity(String requestTokenHeader, City city)//update city
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();

        if (user1.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> deletecity(String requestTokenHeader, String city)//delete city
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();
        if (user1.getRole().equals(Role.ADMIN)) {
            try {
                cityRepository.deleteById(city);
                return new ResponseEntity<>("Deleted!", HttpStatus.ACCEPTED);
            } catch (Exception e) {
                return new ResponseEntity<>("City Not Present", HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }


    //<--------------------------------------------------------CRUD for attractions---------------------------------------------------------->

    public ResponseEntity<?> addattr(String requestTokenHeader, Attractions attraction)//add attraction
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();
        if (user1.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(attractionsRepository.save(attraction), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> updateattr(String requestTokenHeader, Attractions attraction)//update attraction
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();
        if (user1.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(attractionsRepository.save(attraction), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> deleteattr(String requestTokenHeader, int attrid)//delete attractions
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();
        if (user1.getRole().equals(Role.ADMIN)) {
            try {
                attractionsRepository.deleteById(attrid);
                return new ResponseEntity<>("Deleted!", HttpStatus.ACCEPTED);
            } catch (Exception e) {
                return new ResponseEntity<>("Attraction Not Present", HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    //get All Attractions
    public ResponseEntity<?> getAttraction() {
        return new ResponseEntity<>(attractionsRepository.findAll(), HttpStatus.OK);
    }


    //<--------------------------------------------------------CRUD for Restaurants----------------------------------------------------------->
    //Adding Restaurant
    public ResponseEntity<?> addRestaurant(String requestTokenHeader, Restaurant restaurant) {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();
        if (user1.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(restaurantRepository.save(restaurant), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    //updating Restaurant
    public ResponseEntity<?> updateRestaurant(String requestTokenHeader, Restaurant restaurant)//update restaurant
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();
        if (user1.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>(restaurantRepository.save(restaurant), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    //Delete Restaurant
    public ResponseEntity<?> deleteRestaurant(String requestTokenHeader, int res_id)//delete restaurant

    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1 = userRepository.findById(user).get();
        if (user1.getRole().equals(Role.ADMIN)) {


            try {


                restaurantRepository.deleteById(res_id);
                return new ResponseEntity<>("Deleted!", HttpStatus.ACCEPTED);
            } catch (Exception e) {
                return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    //get All Restaurants
    public ResponseEntity<?> getRestaurant() {
        return new ResponseEntity<>(restaurantRepository.findAll(), HttpStatus.OK);
    }


    //---------------------------------------------------------------report-------------------------------------------------------------------->
    public ResponseEntity<?> getreports(String requestTokenHeader) {
        String token = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String role = userRepository.findById(username).get().getRole();
        if (role.equalsIgnoreCase("Admin")) {
            return new ResponseEntity<>(reportRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }


    }

    public ResponseEntity<?> deletereport(String requestTokenHeader,int reportid) {
        String token = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String role = userRepository.findById(username).get().getRole();
        if (role.equalsIgnoreCase("Admin")) {
            reportRepository.deleteById(reportid);
            return new ResponseEntity<>("Report Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }

    }
    public ResponseEntity<?> addBus(String requestTokenHeader, Bus bus){
        String token = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String role = userRepository.findById(username).get().getRole();
        if (role.equalsIgnoreCase("Admin")) {
            busRepository.save(bus);
            return new ResponseEntity<>("Bus Added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> removeBus(String requestTokenHeader, Integer bus_id){
        String token = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        String role = userRepository.findById(username).get().getRole();
        if (role.equalsIgnoreCase("Admin")) {
            busRepository.deleteById(bus_id);
            return new ResponseEntity<>("Bus Removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }
    }

}



package com.cityguide.backend.services;

import com.cityguide.backend.entities.*;
import com.cityguide.backend.exceptions.UnAuthorisedException;
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

    @Autowired
    MetroMapRepository metroMapRepository;

    //<------------------------------------------------------------------CRUD for Cities--------------------------------------------------------------------------->

    public ResponseEntity<?> addcity(String requestTokenHeader, City city)//add city
    {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> updatecity(String requestTokenHeader, City city)//update city
    {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            return new ResponseEntity<>(cityRepository.save(city), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> deletecity(String requestTokenHeader, String city)//delete city
    {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
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
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            return new ResponseEntity<>(attractionsRepository.save(attraction), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> updateattr(String requestTokenHeader, Attractions attraction)//update attraction
    {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            return new ResponseEntity<>(attractionsRepository.save(attraction), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> deleteattr(String requestTokenHeader, int attrid)//delete attractions
    {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
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
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            return new ResponseEntity<>(restaurantRepository.save(restaurant), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    //updating Restaurant
    public ResponseEntity<?> updateRestaurant(String requestTokenHeader, Restaurant restaurant)//update restaurant
    {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            return new ResponseEntity<>(restaurantRepository.save(restaurant), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
        }
    }

    //Delete Restaurant
    public ResponseEntity<?> deleteRestaurant(String requestTokenHeader, int res_id)//delete restaurant

    {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {


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
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            return new ResponseEntity<>(reportRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }


    }

    public ResponseEntity<?> deletereport(String requestTokenHeader, int reportid) {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            reportRepository.deleteById(reportid);
            return new ResponseEntity<>("Report Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }

    }

    public ResponseEntity<?> addMetro(String requestTokenHeader, MetroMap metroMap) {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {

            return new ResponseEntity<>(metroMapRepository.save(metroMap), HttpStatus.OK);
        } else {
            throw new UnAuthorisedException();
        }
    }

    public ResponseEntity<?> updateUrl(Integer metro_id, String url) {
        MetroMap metroMap = metroMapRepository.findById(metro_id).get();
        metroMap.setMetromap_img(url);
        metroMapRepository.save(metroMap);
        return new ResponseEntity<>("URL UPDATED", HttpStatus.OK);
    }

    public ResponseEntity<?> getAllBuses(String requestTokenHeader) {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {

            return new ResponseEntity<>(busRepository.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> addBus(String requestTokenHeader, Bus bus) {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            busRepository.save(bus);
            return new ResponseEntity<>("Bus Added", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }
    }

    public ResponseEntity<?> removeBus(String requestTokenHeader, Integer bus_id) {
        String username = jwtTokenUtil.getUserFromToken(requestTokenHeader);
        if (isAdmin(username)) {
            busRepository.deleteById(bus_id);
            return new ResponseEntity<>("Bus Removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.OK);
        }
    }

    public Boolean isAdmin(String username) {
        String role = userRepository.findById(username).get().getRole();
        return role.equalsIgnoreCase(Role.ADMIN);
    }

}



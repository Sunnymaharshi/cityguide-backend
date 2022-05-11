package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.City;
import com.cityguide.backend.entities.Restaurant;
import com.cityguide.backend.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/addrest",method = RequestMethod.POST) //add city
    public ResponseEntity<?> addrest(@RequestHeader("Authorization") String requestToken, @RequestBody Restaurant restaurant)
    {
        return adminService.addRestaurant(requestToken,restaurant);

    }
    @RequestMapping(value = "/updaterest",method = RequestMethod.PUT) // update city
    public ResponseEntity<?> updaterest(@RequestHeader("Authorization") String requestToken, @RequestBody Restaurant restaurant)
    {
        return adminService.updateRestaurant(requestToken,restaurant);
    }

    @RequestMapping(value = "/deleterest/{resid}",method = RequestMethod.DELETE) // delete city
    public ResponseEntity<?> deleterest(@RequestHeader("Authorization") String requestToken, @PathVariable("resid") int res_id)
    {
        return adminService.deleteRestaurant(requestToken,res_id);
    }
}

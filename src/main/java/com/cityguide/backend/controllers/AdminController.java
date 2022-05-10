package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.City;
import com.cityguide.backend.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/addcity",method = RequestMethod.POST)
    public ResponseEntity<City> addcity(@RequestHeader("Authorization") String requestToken, @RequestBody City city)
    {
        return adminService.addcity(requestToken,city);

    }
}

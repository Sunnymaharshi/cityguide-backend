package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.*;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.BusMapRepository;
import com.cityguide.backend.repositories.MetroMapRepository;
import com.cityguide.backend.repositories.UserRepository;
import com.cityguide.backend.services.AdminService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;


    @Autowired
    MetroMapRepository metroMapRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BusMapRepository busMapRepository;
    // <-------------------------------------------------------CUD For Cities---------------------------------------------------------->

    @RequestMapping(value = "/addcity",method = RequestMethod.POST) //add city
    public ResponseEntity<?> addcity(@RequestHeader("Authorization") String requestToken, @RequestBody City city)
    {
        return adminService.addcity(requestToken,city);

    }
    @RequestMapping(value = "/updatecity",method = RequestMethod.PUT) // update city
    public ResponseEntity<?> updatecity(@RequestHeader("Authorization") String requestToken, @RequestBody City city)
    {
        return adminService.updatecity(requestToken,city);
    }

    @RequestMapping(value = "/deletecity/{city}",method = RequestMethod.DELETE) // delete city
    public ResponseEntity<?> deletecity(@RequestHeader("Authorization") String requestToken, @PathVariable("city") String city)
    {
        return adminService.deletecity(requestToken,city);
    }


    //<---------------------------------------------------------CUD for Attractions---------------------------------------------------->

    @RequestMapping(value = "/getattr",method = RequestMethod.GET) //add rest
    public ResponseEntity<?> getattr()
    {
        return adminService.getAttraction();
    }

    @RequestMapping(value = "/addattr",method = RequestMethod.POST) //add attraction
    public ResponseEntity<?> addattr(@RequestHeader("Authorization") String requestToken, @RequestBody Attractions attraction) {
        return adminService.addattr(requestToken, attraction);
    }
    @RequestMapping(value = "/updateattr",method = RequestMethod.PUT) //update attraction
    public ResponseEntity<?> updateattr(@RequestHeader("Authorization") String requestToken, @RequestBody Attractions attraction) {
        return adminService.addattr(requestToken, attraction);
    }
    @RequestMapping(value = "/deleteattr/{attrid}",method = RequestMethod.DELETE) //delete attraction
    public ResponseEntity<?> deleteattr(@RequestHeader("Authorization") String requestToken, @PathVariable("attrid") int attrid) {
        return adminService.deleteattr(requestToken, attrid);
    }

    //<---------------------------------------------------------CUD for Rest------------------------------------------------------------>

    @RequestMapping(value = "/getrest",method = RequestMethod.GET) //add rest
    public ResponseEntity<?> getrest()
    {
        return adminService.getRestaurant();
    }

    @RequestMapping(value = "/addrest",method = RequestMethod.POST) //add rest
    public ResponseEntity<?> addrest(@RequestHeader("Authorization") String requestToken, @RequestBody Restaurant restaurant)
    {
        return adminService.addRestaurant(requestToken,restaurant);
    }

    @RequestMapping(value = "/updaterest",method = RequestMethod.PUT) // update rest
    public ResponseEntity<?> updaterest(@RequestHeader("Authorization") String requestToken, @RequestBody Restaurant restaurant)
    {
        return adminService.updateRestaurant(requestToken,restaurant);
    }

    @RequestMapping(value = "/deleterest/{resid}",method = RequestMethod.DELETE) // delete rest
    public ResponseEntity<?> deleterest(@RequestHeader("Authorization") String requestToken, @PathVariable("resid") int res_id) {
        return adminService.deleteRestaurant(requestToken, res_id);
    }

    //<----------------------------------------------------------Bus Map,MetroMap-------------------------------------------------------------------->
    @RequestMapping(value = "/addmetro",method = RequestMethod.POST)
    public ResponseEntity<?> addmetro(@RequestHeader("Authorization") String requestToken,@RequestBody MetroMap metroMap)
    {
        String token=requestToken.substring(7);
        String username=jwtTokenUtil.getUsernameFromToken(token);
        String role=userRepository.findById(username).get().getRole();
        if(role.equalsIgnoreCase("Admin"))
        {
            return new ResponseEntity<>( metroMapRepository.save(metroMap), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Unauthorized",HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/addbus",method = RequestMethod.POST)
    public ResponseEntity<?> addbus(@RequestHeader("Authorization") String requestToken,@RequestBody BusMap busMap)
    {
        String token=requestToken.substring(7);
        String username=jwtTokenUtil.getUsernameFromToken(token);
        String role=userRepository.findById(username).get().getRole();
        if(role.equalsIgnoreCase("Admin"))
        {
            return new ResponseEntity<>( busMapRepository.save(busMap), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Unauthorized",HttpStatus.FORBIDDEN);
        }
    }

    //------------------------------------------------------Get All Reports------------------------------------------------------------------------------>
    @RequestMapping(value = "/getreports",method = RequestMethod.GET)
    public ResponseEntity<?> getReports(@RequestHeader("Authorization") String requestToken)
    {
        return adminService.getreports(requestToken);
    }

}

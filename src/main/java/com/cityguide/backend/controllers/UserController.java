package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.Answer;
import com.cityguide.backend.entities.City;
import com.cityguide.backend.entities.Question;
import com.cityguide.backend.entities.User;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.QuestionRepository;
import com.cityguide.backend.repositories.UserRepository;
import com.cityguide.backend.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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



    // Operations for Questions
    @RequestMapping(value = "/postques",method = RequestMethod.POST)
    public ResponseEntity<Question> postques(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Question question)
    {

        return userServices.postques(requestTokenHeader,question);
    }
    //Api for posting answers
    @RequestMapping(value = "/postans",method = RequestMethod.POST)
    public ResponseEntity<Answer> postans(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Answer answer)
    {

        return userServices.postans(requestTokenHeader,answer);
    }

    //Api for get answers for a question
    @RequestMapping(value = "/getanswers/{quesid}",method = RequestMethod.GET)
    public ResponseEntity<Question> getanswers(@PathVariable("quesid") int ques_id){
        return userServices.getanswers(ques_id);
    }


    //User operations for Cities

    @RequestMapping(value = "/getallcities",method = RequestMethod.GET)
    public ResponseEntity<List<City>> getcities()
    {
        return userServices.getcities();
    }

    @RequestMapping(value = "/getcity/{city}",method = RequestMethod.GET)
    public ResponseEntity<City> getcities(@PathVariable("city") String city)
    {
        return userServices.getcity(city);
    }
}

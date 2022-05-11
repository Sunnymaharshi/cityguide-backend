package com.cityguide.backend.services;

import com.cityguide.backend.entities.*;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    CityRepository cityRepository;


    public ResponseEntity<String> signup(User user)
    {
        Optional<User> check= userRepository.findById(user.getUsername());
        if(check.isPresent())
        {
            return new ResponseEntity<>("User with username already Exists", HttpStatus.FORBIDDEN);
        }
         userRepository.save(user);
         return new ResponseEntity<>("User Signed In Successfully",HttpStatus.ACCEPTED);
    }

    //<------------------------------------------------------User Operations for Questions--------------------------------------------------->

    public ResponseEntity<Question> postques(String requestTokenHeader, Question question)
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        question.setUsername(user);
        return  new ResponseEntity<>(questionRepository.save(question),HttpStatus.ACCEPTED);
    }

    //<--------------------------------------------------------User Operations for Answers---------------------------------------------------->
    public ResponseEntity<Answer> postans(String requestTokenHeader, Answer answer)
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        answer.setUsername(user);
        return  new ResponseEntity<>(answerRepository.save(answer),HttpStatus.ACCEPTED);
    }
    //Api for getting all answers for a question
     public ResponseEntity<List<Answer>> getanswers(int ques_id){
        try{
            return new ResponseEntity<>(questionRepository.findById(ques_id).get().getAnswerList(),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
     }
     //<-------------------------------------------------------User Service for posting comments----------------------------------------------->
    public ResponseEntity<Comment> postcmnt(String requestTokenHeader, Comment comment){
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        comment.setUsername(user);
        return new ResponseEntity<>(commentRepository.save(comment),HttpStatus.ACCEPTED);
    }

    //User Service for getting all comments for an answer
    public ResponseEntity<List<Comment>> getcmnts(int ans_id){
        try {
            return new ResponseEntity<>(answerRepository.findById(ans_id).get().getCommentList(), HttpStatus.OK);
        }
        catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }


    //<----------------------------------------------------------User Operation for Cities------------------------------------------->

    public ResponseEntity<List<City>> getcities() //get All cities
    {
        return new ResponseEntity<>(cityRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<City> getcity(String city)//get a single city
    {
        try {

            return new ResponseEntity<>(cityRepository.findById(city).get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    //Reading all restaurants by city name
    public ResponseEntity<List<Restaurant>> getRestaurants(String city){
        try {
            return new ResponseEntity<>(cityRepository.findById(city).get().getRestaurantList(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    //Get all Attractions of a city
    public ResponseEntity<List<Attractions>> getattr(String city){
        try {
            return new ResponseEntity<>(cityRepository.findById(city).get().getAttractionsList(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}

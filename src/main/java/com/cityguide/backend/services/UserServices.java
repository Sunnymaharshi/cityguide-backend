package com.cityguide.backend.services;

import com.cityguide.backend.entities.*;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ResponseEntity<?> getuserdetails(String requestTokenHeader)
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        return new ResponseEntity<>(userRepository.findById(user).get(),HttpStatus.OK);
    }
    public ResponseEntity<?> getusername(String requestTokenHeader)
    {
       try {
            String jwtToken = requestTokenHeader.substring(7);
            String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
            User curruser=userRepository.findById(user).get();
            Authobject authobject=new Authobject(curruser.getUsername(), curruser.getRole());
            return new ResponseEntity<>(authobject,HttpStatus.OK);
        }
       catch (ExpiredJwtException e)
       {
           return new ResponseEntity<>("Token Expired",HttpStatus.OK);
       }
    }

    //<------------------------------------------------------User Operations for Questions--------------------------------------------------->

    //posting question
    public ResponseEntity<Question> postques(String requestTokenHeader, Question question)
    {
        String jwtToken = requestTokenHeader.substring(7);
        String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        question.setUsername(user);
        return  new ResponseEntity<>(questionRepository.save(question),HttpStatus.ACCEPTED);
    }

    //deleting question
    public ResponseEntity<?> delques(String requestTokenHeader, int ques_id){
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        Question user2= questionRepository.findById(ques_id).get();
        if(user1.getUsername().equals(user2.getUsername())){
        try{
            questionRepository.deleteById(ques_id);
            return  new  ResponseEntity<>("Question Deleted",HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Question Not Found",HttpStatus.NOT_FOUND);
        }
        }
        else{
            return new ResponseEntity<>("Unauthorized!", HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<?> getuserques(String requestTokenHeader)
    {
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        return new ResponseEntity<>(user1.getQuestionList(),HttpStatus.OK);
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
     //Api for deleting Answer
     public ResponseEntity<?> delans(String requestTokenHeader, int ans_id){
         String jwtToken=requestTokenHeader.substring(7);
         String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
         User user1=userRepository.findById(user).get();
         Answer user2;
         try {
              user2 = answerRepository.findById(ans_id).get();
         }
         catch (Exception e)
         {
             return new ResponseEntity<>("Answer Not Found",HttpStatus.NOT_FOUND);
         }
         if(user1.getUsername().equals(user2.getUsername())){
             try{
                 answerRepository.deleteById(ans_id);
                 return  new  ResponseEntity<>("Answer Deleted",HttpStatus.ACCEPTED);
             }
             catch (Exception e){
                 return new ResponseEntity<>("Answer Not Found",HttpStatus.NOT_FOUND);
             }
         }
         else{
             return new ResponseEntity<>("Unauthorized!", HttpStatus.FORBIDDEN);
         }
     }

     //Api for updating Answer
     public ResponseEntity<?> updateans(String requestTokenHeader,Answer answer)
     {
         String jwtToken = requestTokenHeader.substring(7);
         String user = jwtTokenUtil.getUsernameFromToken(jwtToken);
         User user1=userRepository.findById(user).get();
         Answer user2= answerRepository.findById(answer.getAns_id()).get();
         if(user1.getUsername().equals(user2.getUsername()))
         {
             Answer answer1= new Answer();
             answer1.setUsername(user2.getUsername());
             answer1.setAns_id(answer.getAns_id());
             answer1.setQues_id(user2.getQues_id());
             answer1.setDescription(answer.getDescription());
             answer1.setDownvotes(user2.getDownvotes());
             answer1.setUpvotes(user2.getUpvotes());
             answer1.setFreq(user2.getFreq());
             answer1.setCommentList(user2.getCommentList());
             return new ResponseEntity<>(answerRepository.save(answer1), HttpStatus.ACCEPTED);
         }
         else {
             return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
         }
     }
     //<-------------------------------------------------------User Service for  comments----------------------------------------------->
    public ResponseEntity<Comment> postcmnt(String requestTokenHeader, Comment comment){//posting comments
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

    //deleting comments
    public ResponseEntity<?> delcomm(String requestTokenHeader, int comm_id){
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        User user1=userRepository.findById(user).get();
        Comment user2= commentRepository.findById(comm_id).get();
        if(user1.getUsername().equals(user2.getUsername())){
            try{
                commentRepository.deleteById(comm_id);
                return  new  ResponseEntity<>("Comment Deleted",HttpStatus.ACCEPTED);
            }
            catch (Exception e){
                return new ResponseEntity<>("Comment Not found",HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>("Unauthorized!", HttpStatus.FORBIDDEN);
        }
    }
    //update comments
    public ResponseEntity<?> updatecmnt(String requestTokenHeader, Comment comment){
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        Comment c=commentRepository.findById(comment.getComm_id()).get();
        if(user.equals(c.getUsername())) {
            comment.setUsername(user);
            comment.setAns_id(c.getAns_id());
            return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.ACCEPTED);
        }
        else
        {
            return new ResponseEntity<>("Unauthorized",HttpStatus.FORBIDDEN);
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

    //get all city names
    public ResponseEntity<?> getCityNames(){
        List<City> cityList=cityRepository.findAll();
        List<String > citynames=new ArrayList<>();
        for(City c: cityList){
            citynames.add(c.getCity_name());
        }
        return new ResponseEntity<>(citynames,HttpStatus.OK);
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

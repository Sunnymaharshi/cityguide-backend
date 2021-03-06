package com.cityguide.backend.services;

import com.cityguide.backend.response.checkvotes;
import com.cityguide.backend.response.mCity;
import com.cityguide.backend.entities.*;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import com.cityguide.backend.response.Authobject;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;

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

    @Autowired
    UpvoteRepository upvoteRepository;

    @Autowired
    DownvoteRepository downvoteRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    BookMarkRepository bookMarkRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    AttractionsRepository attractionsRepository;

    @Autowired
    PasswordEncoder bcryptEncoder;


    public ResponseEntity<String> signup(User user)
    {
        Optional<User> check= userRepository.findById(user.getUsername());
        if(check.isPresent())
        {
            return new ResponseEntity<>("User with username already Exists", HttpStatus.FORBIDDEN);
        }
        String pass= user.getPassword();
        String code=bcryptEncoder.encode(pass);
        user.setPassword(code);
         userRepository.save(user);
         return new ResponseEntity<>("User Signed In Successfully",HttpStatus.ACCEPTED);
    }
    public ResponseEntity<?> getuserdetails(String requestTokenHeader)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        return new ResponseEntity<>(userRepository.findById(username).get(),HttpStatus.OK);
    }
    public ResponseEntity<?> getusername(String requestTokenHeader)
    {
       try {
            String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
            User curruser=userRepository.findById(username).get();
            Authobject authobject=new Authobject(curruser.getUsername(), curruser.getRole());
            return new ResponseEntity<>(authobject,HttpStatus.OK);
        }
       catch (ExpiredJwtException e)
       {
           return new ResponseEntity<>("Token Expired",HttpStatus.OK);
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
    //get only city data by name
    public ResponseEntity<?> getmcity(String cityname){

        try {
            City city=cityRepository.findById(cityname).get();
            mCity display=new mCity(city.getCity_name(),city.getCity_desc(),city.getCity_tagline());
            return new ResponseEntity<>(display,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("City Not Found",HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> getrestbyid(int id)
    {
        try {
            return new ResponseEntity<>(restaurantRepository.findById(id).get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<?> getattrbyid(int id)
    {
        try {
            return new ResponseEntity<>(attractionsRepository.findById(id).get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }

    //<---------------------------------------------------Upvote,DownVote-------------------------------------------------------------->
    public ResponseEntity<?> upvote(String requestTokenHeader,int ansid)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        try {
            Upvote upvote = new Upvote();
            upvote.setAns_id(ansid);
            upvote.setUsername(username);
            Optional<Downvote> check = downvoteRepository.findUserDownvote(username, ansid);
            if (check.isPresent()) {
                Answer answer = answerRepository.findById(ansid).get();
                answer.setDownvotes(answer.getDownvotes() - 1);
                answer.setUpvotes(answer.getUpvotes()+1);
                answerRepository.save(answer);
                Downvote d=downvoteRepository.findDownvote(username,ansid);
                downvoteRepository.delete(d);
                upvoteRepository.save(upvote);
                return new ResponseEntity<>("Upvoted!!", HttpStatus.OK);
            }

            upvoteRepository.save(upvote);
        }
        catch (Exception e)
        {
            Upvote u=upvoteRepository.findUpvote(username,ansid);
            upvoteRepository.delete(u);
            Answer answer=answerRepository.findById(ansid).get();
            answer.setUpvotes(answer.getUpvotes()-1);
            answerRepository.save(answer);
            return new ResponseEntity<>("Upvote Removed!!",HttpStatus.OK);
        }

        Answer answer=answerRepository.findById(ansid).get();
        answer.setUpvotes(answer.getUpvotes()+1);

        answerRepository.save(answer);
        return  new ResponseEntity<>("Upvoted!!",HttpStatus.OK);
    }

    public ResponseEntity<?> downvote(String requestTokenHeader,int ansid)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        try {
            Downvote downvote = new Downvote();
            downvote.setAns_id(ansid);
            downvote.setUsername(username);
            Optional<Upvote> check = upvoteRepository.findUserUpvote(username, ansid);
            if (check.isPresent()) {
                Answer answer = answerRepository.findById(ansid).get();
                answer.setUpvotes(answer.getUpvotes() - 1);
                answer.setDownvotes(answer.getDownvotes()+1);
                answerRepository.save(answer);
                Upvote v=upvoteRepository.findUpvote(username,ansid);
                upvoteRepository.delete(v);
                downvoteRepository.save(downvote);
                return new ResponseEntity<>("Downvoted!!", HttpStatus.OK);
            } else {
                downvoteRepository.save(downvote);
            }
        }
        catch (Exception e)
        {
            Downvote d=downvoteRepository.findDownvote(username,ansid);
            downvoteRepository.delete(d);
            Answer answer=answerRepository.findById(ansid).get();
            answer.setDownvotes(answer.getDownvotes()-1);
            answerRepository.save(answer);
            return new ResponseEntity<>("Downvote Removed!",HttpStatus.OK);
        }

        Answer answer=answerRepository.findById(ansid).get();
        answer.setDownvotes(answer.getDownvotes()+1);
        answerRepository.save(answer);
        return  new ResponseEntity<>("Downvoted!!",HttpStatus.OK);
    }

    public ResponseEntity<?> check(String requestTokenHeader,int ansid)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        checkvotes c=new checkvotes();
        Optional<Upvote> h=upvoteRepository.findUserUpvote(username,ansid);
        Optional<Downvote> d=downvoteRepository.findUserDownvote(username,ansid);
        if(h.isPresent())
        {
            c.setHasupvoted(true);
            return new ResponseEntity<>(c,HttpStatus.OK);
        }
        else if(d.isPresent())
        {
            c.setHasdownvoted(true);
            return new ResponseEntity<>(c,HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(c,HttpStatus.OK);
        }

    }
    //---------------------------------------------------------------Report a ques-------------------------------------------------------------------------->
    public ResponseEntity<?> reportques(String type,int typeid)
    {
        Report report=new Report();
        report.setReport_type(type);
        report.setReport_type_id(typeid);
        String desc="";
        if(type.equalsIgnoreCase("comment"))
        {
            desc=commentRepository.findById(typeid).get().getDescription();
        }
        else if(type.equalsIgnoreCase("Answer"))
        {
            desc=answerRepository.findById(typeid).get().getDescription();
        }
        else if(type.equalsIgnoreCase("Question"))
        {
            desc=questionRepository.findById(typeid).get().getDescription();
        }
        report.setReport_desc(desc);
        try {
            return new ResponseEntity<>(reportRepository.save(report), HttpStatus.OK);
        }
        catch (Exception e){
            Report k=reportRepository.findReport(type,typeid);
           return new ResponseEntity<>(k,HttpStatus.OK);
        }
    }
    //-------------------------------------------------------------------BookMark---------------------------------------------------------------------------->

    public ResponseEntity<?> addbookmark(String requestTokenHeader ,String type,int typeid)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        BookMarks bookMarks=new BookMarks();
        bookMarks.setBook_type(type);
        bookMarks.setBook_type_id(typeid);
        bookMarks.setUsername(username);
        try {
            return new ResponseEntity<>(bookMarkRepository.save(bookMarks), HttpStatus.OK);
        }
        catch (Exception e){
            BookMarks b=bookMarkRepository.findbookmark(type,typeid);
            return new ResponseEntity<>(b,HttpStatus.OK);
        }
    }
    public ResponseEntity<?> getuserbookmark(String requestTokenHeader)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        User curr_user=userRepository.findById(username).get();
        return new ResponseEntity<>(curr_user.getBookMarksList(),HttpStatus.OK);
    }

    }





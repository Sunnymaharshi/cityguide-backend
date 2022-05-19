package com.cityguide.backend.services;

import com.cityguide.backend.CustomResponses.checkvotes;
import com.cityguide.backend.CustomResponses.mAnswer;
import com.cityguide.backend.CustomResponses.mCity;
import com.cityguide.backend.CustomResponses.mQuestion;
import com.cityguide.backend.entities.*;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    // get all questions
    public ResponseEntity<?> getAllQuestions(String city){
        List<Question> questionList=cityRepository.findById(city).get().getQuestionList();
        List<mQuestion> display=new ArrayList<>();
        for (Question q:questionList)
        {
            display.add(new mQuestion(q.getQues_id(),q.getDescription(),q.getUsername(),q.getCity_name()));
        }
        Collections.sort(display,new Comparator<mQuestion>(){
            @Override
            public int compare(mQuestion q1,mQuestion q2)
            {
                return Integer.compare(q2.getQues_id(), q1.getQues_id());
            }
        });

        return new ResponseEntity<>(display,HttpStatus.OK);
    }
    //get ques by id
    public ResponseEntity<?> getQuestions(int id){
        try {

            return new ResponseEntity<>(questionRepository.findById(id).get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }

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
     public ResponseEntity<?> getanswers(int ques_id){
        try{
            List<Answer> answerList=questionRepository.findById(ques_id).get().getAnswerList();
            List<mAnswer> display=new ArrayList<>();

            for(Answer a:answerList)
            {
                display.add(new mAnswer(a.getAns_id(),a.getDescription(),a.getFreq(),a.getUpvotes(),a.getDownvotes(),a.getQues_id(),a.getUsername()));
            }
            Collections.sort(display,new Comparator<mAnswer>(){
                @Override
                public int compare(mAnswer a1,mAnswer a2)
                {
                    return Integer.compare(a2.getAns_id(), a1.getAns_id());
                }
            });
            return new ResponseEntity<>(display,HttpStatus.OK);
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

     //get ans by id
     public ResponseEntity<?> getansbyid(int ansid)
     {
         Answer answer=answerRepository.findById(ansid).get();
         mAnswer display=new mAnswer(answer.getAns_id(),answer.getDescription(),answer.getFreq(),answer.getUpvotes(),answer.getDownvotes(),answer.getQues_id(),answer.getUsername());
         return new ResponseEntity<>(display,HttpStatus.OK);
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
            List<Comment> commentList=answerRepository.findById(ans_id).get().getCommentList();
            Collections.sort(commentList,new Comparator<Comment>(){
                @Override
                public int compare(Comment c1,Comment c2)
                {
                    return Integer.compare(c2.getComm_id(), c1.getComm_id());
                }
            });
            return new ResponseEntity<>(commentList, HttpStatus.OK);
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
    //get only city data by name
    public ResponseEntity<?> getmcity(String cityname){

        try {
            City city=cityRepository.findById(cityname).get();
            mCity display=new mCity(city.getCity_name(),city.getCity_desc(),city.getCity_image());
            return new ResponseEntity<>(display,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("City Not Found",HttpStatus.NOT_FOUND);
        }

    }

    //<---------------------------------------------------Upvote,DownVote-------------------------------------------------------------->
    public ResponseEntity<?> upvote(String requestTokenHeader,int ansid)
    {
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        try {
            Upvote upvote = new Upvote();
            upvote.setAns_id(ansid);
            upvote.setUsername(user);
            Optional<Downvote> check = downvoteRepository.findUserDownvote(user, ansid);
            if (check.isPresent()) {
                Answer answer = answerRepository.findById(ansid).get();
                answer.setDownvotes(answer.getDownvotes() - 1);
                answer.setUpvotes(answer.getUpvotes()+1);
                answerRepository.save(answer);
                Downvote d=downvoteRepository.findDownvote(user,ansid);
                downvoteRepository.delete(d);
                upvoteRepository.save(upvote);
                return new ResponseEntity<>("Upvoted!!", HttpStatus.OK);
            }

            upvoteRepository.save(upvote);
        }
        catch (Exception e)
        {
            Upvote u=upvoteRepository.findUpvote(user,ansid);
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
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        try {
            Downvote downvote = new Downvote();
            downvote.setAns_id(ansid);
            downvote.setUsername(user);
            Optional<Upvote> check = upvoteRepository.findUserUpvote(user, ansid);
            if (check.isPresent()) {
                Answer answer = answerRepository.findById(ansid).get();
                answer.setUpvotes(answer.getUpvotes() - 1);
                answer.setDownvotes(answer.getDownvotes()+1);
                answerRepository.save(answer);
                Upvote v=upvoteRepository.findUpvote(user,ansid);
                upvoteRepository.delete(v);
                downvoteRepository.save(downvote);
                return new ResponseEntity<>("Downvoted!!", HttpStatus.OK);
            } else {
                downvoteRepository.save(downvote);
            }
        }
        catch (Exception e)
        {
            Downvote d=downvoteRepository.findDownvote(user,ansid);
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
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        checkvotes c=new checkvotes();
        Optional<Upvote> h=upvoteRepository.findUserUpvote(user,ansid);
        Optional<Downvote> d=downvoteRepository.findUserDownvote(user,ansid);
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



    //--------------------------------------------------------------Get Similar Questions----------------------------------------------------------------->
    public ResponseEntity<?> getsimilarques(String query,String city)
    {
       List<Question> questionList=cityRepository.findById(city).get().getQuestionList();
       List<Question> displaylist=new ArrayList<>();
       String words[]=query.split(" ");
       for(Question question:questionList) {
           int hit = 0;
           if (question.getDescription().contains(query)) {
               displaylist.add(question);
           } else {
               for (String word : words) {
                   if (question.getDescription().contains(word)) {
                       hit++;
                   }
               }
               if (hit > (words.length / 2)) {
                   displaylist.add(question);
               }
           }
       }
       return new ResponseEntity<>(displaylist,HttpStatus.OK);
    }

}

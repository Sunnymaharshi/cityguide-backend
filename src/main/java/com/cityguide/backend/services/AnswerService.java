package com.cityguide.backend.services;

import com.cityguide.backend.response.mAnswer;
import com.cityguide.backend.response.mockforusernswers;
import com.cityguide.backend.entities.Answer;
import com.cityguide.backend.entities.User;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import com.cityguide.backend.types.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AnswerService {

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

    public ResponseEntity<Answer> postans(String requestTokenHeader, Answer answer)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        answer.setUsername(username);
        return  new ResponseEntity<>(answerRepository.save(answer), HttpStatus.ACCEPTED);
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
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        Answer user2;
        try {
            user2 = answerRepository.findById(ans_id).get();
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Answer Not Found",HttpStatus.NOT_FOUND);
        }
        if(username.equals(user2.getUsername())||isAdmin(username)){
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
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        Answer user2= answerRepository.findById(answer.getAns_id()).get();
        if(username.equals(user2.getUsername()))
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
    public ResponseEntity<?> getuserans(String requestTokenHeader)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        List<Answer> answerList=userRepository.findById(username).get().getAnswerList();
        List<mockforusernswers> display=new ArrayList<>();


        for(Answer a:answerList)
        {
            String desc=questionRepository.findById(a.getQues_id()).get().getDescription();
            display.add(new mockforusernswers(a.getAns_id(),a.getDescription(),a.getFreq(),a.getUpvotes(),a.getDownvotes(),a.getQues_id(),desc,a.getUsername()));
        }

        return new ResponseEntity<>(display,HttpStatus.OK);
    }
    public Boolean isAdmin(String username){
        String role=userRepository.findById(username).get().getRole();
        return role.equalsIgnoreCase(Role.ADMIN);
    }
}

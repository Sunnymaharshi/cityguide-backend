package com.cityguide.backend.services;

import com.cityguide.backend.entities.Comment;
import com.cityguide.backend.entities.User;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CommentServices {

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

    //post comments
    public ResponseEntity<Comment> postcmnt(String requestTokenHeader, Comment comment){//posting comments
        String jwtToken=requestTokenHeader.substring(7);
        String user=jwtTokenUtil.getUsernameFromToken(jwtToken);
        comment.setUsername(user);
        return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.ACCEPTED);
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

}

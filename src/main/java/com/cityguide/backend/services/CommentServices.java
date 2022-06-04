package com.cityguide.backend.services;

import com.cityguide.backend.entities.Comment;
import com.cityguide.backend.entities.User;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.*;
import com.cityguide.backend.types.Role;
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
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        comment.setUsername(username);
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
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        Comment user2= commentRepository.findById(comm_id).get();
        if(username.equals(user2.getUsername())||isAdmin(username)){
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
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        Comment c=commentRepository.findById(comment.getComm_id()).get();
        if(username.equals(c.getUsername())) {
            comment.setUsername(username);
            comment.setAns_id(c.getAns_id());
            return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.ACCEPTED);
        }
        else
        {
            return new ResponseEntity<>("Unauthorized",HttpStatus.FORBIDDEN);
        }
    }
    public Boolean isAdmin(String username){
        String role=userRepository.findById(username).get().getRole();
        return role.equalsIgnoreCase(Role.ADMIN);
    }

}

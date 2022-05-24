package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.Comment;
import com.cityguide.backend.services.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentServices commentServices;

    @RequestMapping(value = "/postcmnt", method = RequestMethod.POST)
    public ResponseEntity<Comment> postcmnt(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Comment comment) {
        return commentServices.postcmnt(requestTokenHeader, comment);
    }

    //Api for getting all comments for an comment
    @RequestMapping(value = "/getcmnts/{ansid}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getcmnt(@PathVariable("ansid") int ans_id) {
        return commentServices.getcmnts(ans_id);
    }

    //deleting comments
    @RequestMapping(value = "/deletecomm/{commid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delcmnt(@RequestHeader("Authorization") String requestTokenHeader, @PathVariable("commid") int commid) {
        return commentServices.delcomm(requestTokenHeader, commid);
    }

    //update comments
    @RequestMapping(value = "/updatecmnt", method = RequestMethod.PUT)
    public ResponseEntity<?> updatecmnt(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Comment comment) {
        return commentServices.updatecmnt(requestTokenHeader, comment);
    }

}

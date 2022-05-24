package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.Answer;
import com.cityguide.backend.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnswerController {


    @Autowired
    AnswerService answerService;


    @RequestMapping(value = "/postans", method = RequestMethod.POST)
    public ResponseEntity<Answer> postans(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Answer answer) {

        return answerService.postans(requestTokenHeader, answer);
    }

    //Api for get answers for a question
    @RequestMapping(value = "/getanswers/{quesid}", method = RequestMethod.GET)
    public ResponseEntity<?> getanswers(@PathVariable("quesid") int ques_id) {
        return answerService.getanswers(ques_id);
    }

    //Api for deleting answer
    @RequestMapping(value = "/deleteans/{ansid}", method = RequestMethod.DELETE) // delete ANS
    public ResponseEntity<?> deleteans(@RequestHeader("Authorization") String requestToken, @PathVariable("ansid") int ans_id) {
        return answerService.delans(requestToken, ans_id);
    }

    //Api for updating answer
    @RequestMapping(value = "/updateans", method = RequestMethod.PUT) // update ans
    public ResponseEntity<?> updateans(@RequestHeader("Authorization") String requestToken, @RequestBody Answer answer) {
        return answerService.updateans(requestToken, answer);
    }

    @RequestMapping(value = "/getanswer/{ansid}", method = RequestMethod.GET) //get ans by id
    public ResponseEntity<?> getanswer(@PathVariable("ansid") int ans_id) {
        return answerService.getansbyid(ans_id);
    }

    @RequestMapping(value = "/getuseranswers", method = RequestMethod.GET) //get ans by id
    public ResponseEntity<?> getanswer(@RequestHeader("Authorization") String requestToken) {
        return answerService.getuserans(requestToken);
    }

}

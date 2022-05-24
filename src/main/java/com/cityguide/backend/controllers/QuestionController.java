package com.cityguide.backend.controllers;


import com.cityguide.backend.entities.Question;
import com.cityguide.backend.services.QuestionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuestionController {


    @Autowired
    QuestionServices questionServices;




    @RequestMapping(value = "/postques", method = RequestMethod.POST)
    public ResponseEntity<Question> postques(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Question question) {

        return questionServices.postques(requestTokenHeader, question);
    }

    //delete question Api
    @RequestMapping(value = "/delques/{quesid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delques(@RequestHeader("Authorization") String requestTokenHeader, @PathVariable("quesid") int ques_id) {
        return questionServices.delques(requestTokenHeader, ques_id);
    }

    @RequestMapping(value = "/getUserQues", method = RequestMethod.GET)
    public ResponseEntity<?> getUserques(@RequestHeader("Authorization") String RequestTokenHeader) {
        return questionServices.getuserques(RequestTokenHeader);
    }

    @RequestMapping(value = "/getAllQues/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllQues(@PathVariable("city") String city) {
        return questionServices.getAllQuestions(city);
    }

    @RequestMapping(value = "/getQues/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getQues(@PathVariable("id") int id) {
        return questionServices.getQuestions(id);
    }

    @RequestMapping(value = "/getsimques/{city}/{query}", method = RequestMethod.GET)
    public ResponseEntity<?> getuserdetails(@PathVariable("city") String city, @PathVariable("query") String query) {
        return questionServices.getsimilarques(query, city);
    }
}

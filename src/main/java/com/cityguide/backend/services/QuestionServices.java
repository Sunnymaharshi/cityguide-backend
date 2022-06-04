package com.cityguide.backend.services;

import com.cityguide.backend.response.mQuestion;
import com.cityguide.backend.entities.Question;
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
public class QuestionServices {

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

        return new ResponseEntity<>(display, HttpStatus.OK);
    }
    //get ques by id
    public ResponseEntity<?> getQuestions(int id){
        try {

            Question q=questionRepository.findById(id).get();
            mQuestion display=new mQuestion(q.getQues_id(),q.getDescription(),q.getUsername(),q.getCity_name());
            return new ResponseEntity<>(display, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        }
    }

    //posting question
    public ResponseEntity<Question> postques(String requestTokenHeader, Question question)
    {
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        question.setUsername(username);
        return  new ResponseEntity<>(questionRepository.save(question),HttpStatus.ACCEPTED);
    }

    //deleting question
    public ResponseEntity<?> delques(String requestTokenHeader, int ques_id){
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        Question user2= questionRepository.findById(ques_id).get();
        if(username.equals(user2.getUsername())||isAdmin(username)){
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
        String username=jwtTokenUtil.getUserFromToken(requestTokenHeader);
        List<Question> questionList=userRepository.findById(username).get().getQuestionList();
        List<mQuestion> display=new ArrayList<>();
        for (Question q:questionList)
        {
            display.add(new mQuestion(q.getQues_id(),q.getDescription(),q.getUsername(),q.getCity_name()));
        }
        return new ResponseEntity<>(display,HttpStatus.OK);
    }


    public ResponseEntity<?> getsimilarques(String query,String city)
    {
        List<Question> questionList=cityRepository.findById(city).get().getQuestionList();
        List<mQuestion> displaylist=new ArrayList<>();
        String words[]=query.split(" ");
        for(Question question:questionList) {
            int hit = 0;
            if (question.getDescription().contains(query)) {
                displaylist.add(new mQuestion(question.getQues_id(),question.getDescription(),question.getUsername(),question.getCity_name()));
            } else {
                for (String word : words) {
                    if (question.getDescription().contains(word)) {
                        hit++;
                    }
                }
                if (hit > (words.length / 2)) {
                    displaylist.add(new mQuestion(question.getQues_id(),question.getDescription(),question.getUsername(),question.getCity_name()));
                }
            }
        }
        return new ResponseEntity<>(displaylist,HttpStatus.OK);
    }
    public Boolean isAdmin(String username){
        String role=userRepository.findById(username).get().getRole();
        return role.equalsIgnoreCase(Role.ADMIN);
    }

}

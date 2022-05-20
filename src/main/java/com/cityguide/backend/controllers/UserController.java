package com.cityguide.backend.controllers;

import com.cityguide.backend.entities.*;
import com.cityguide.backend.jwt.JwtTokenUtil;
import com.cityguide.backend.repositories.QuestionRepository;
import com.cityguide.backend.repositories.UserRepository;
import com.cityguide.backend.services.UserServices;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.protobuf.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.cloud.storage.Acl.User.ofAllUsers;

@RestController
public class UserController {


    @Autowired
    UserServices userServices;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Storage storage;


    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signup(@RequestBody User user) {
        return userServices.signup(user);
    }


    //<-----------------------------------------------------------Operations for Questions----------------------------------------------------->
    @RequestMapping(value = "/postques", method = RequestMethod.POST)
    public ResponseEntity<Question> postques(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Question question) {

        return userServices.postques(requestTokenHeader, question);
    }

    //delete question Api
    @RequestMapping(value = "/delques/{quesid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delques(@RequestHeader("Authorization") String requestTokenHeader, @PathVariable("quesid") int ques_id) {
        return userServices.delques(requestTokenHeader, ques_id);
    }

    @RequestMapping(value = "/getUserQues", method = RequestMethod.GET)
    public ResponseEntity<?> getUserques(@RequestHeader("Authorization") String RequestTokenHeader) {
        return userServices.getuserques(RequestTokenHeader);
    }

    @RequestMapping(value = "/getAllQues/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllQues(@PathVariable("city") String city) {
        return userServices.getAllQuestions(city);
    }

    @RequestMapping(value = "/getQues/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getQues(@PathVariable("id") int id) {
        return userServices.getQuestions(id);
    }

    //<------------------------------------------------------------Operation for Answers------------------------------------------------------->
    //Api for posting answers
    @RequestMapping(value = "/postans", method = RequestMethod.POST)
    public ResponseEntity<Answer> postans(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Answer answer) {

        return userServices.postans(requestTokenHeader, answer);
    }

    //Api for get answers for a question
    @RequestMapping(value = "/getanswers/{quesid}", method = RequestMethod.GET)
    public ResponseEntity<?> getanswers(@PathVariable("quesid") int ques_id) {
        return userServices.getanswers(ques_id);
    }

    //Api for deleting answer
    @RequestMapping(value = "/deleteans/{ansid}", method = RequestMethod.DELETE) // delete ANS
    public ResponseEntity<?> deleteans(@RequestHeader("Authorization") String requestToken, @PathVariable("ansid") int ans_id) {
        return userServices.delans(requestToken, ans_id);
    }

    //Api for updating answer
    @RequestMapping(value = "/updateans", method = RequestMethod.PUT) // update ans
    public ResponseEntity<?> updateans(@RequestHeader("Authorization") String requestToken, @RequestBody Answer answer) {
        return userServices.updateans(requestToken, answer);
    }

    @RequestMapping(value = "/getanswer/{ansid}", method = RequestMethod.GET) //get ans by id
    public ResponseEntity<?> getanswer(@PathVariable("ansid") int ans_id) {
        return userServices.getansbyid(ans_id);
    }


    //<----------------------------------------------------------Operation For Comments----------------------------------------------------->
    //Api for posting a comment
    @RequestMapping(value = "/postcmnt", method = RequestMethod.POST)
    public ResponseEntity<Comment> postcmnt(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Comment comment) {
        return userServices.postcmnt(requestTokenHeader, comment);
    }

    //Api for getting all comments for an comment
    @RequestMapping(value = "/getcmnts/{ansid}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getcmnt(@PathVariable("ansid") int ans_id) {
        return userServices.getcmnts(ans_id);
    }

    //deleting comments
    @RequestMapping(value = "/deletecomm/{commid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delcmnt(@RequestHeader("Authorization") String requestTokenHeader, @PathVariable("commid") int commid) {
        return userServices.delcomm(requestTokenHeader, commid);
    }

    //update comments
    @RequestMapping(value = "/updatecmnt", method = RequestMethod.PUT)
    public ResponseEntity<?> updatecmnt(@RequestHeader("Authorization") String requestTokenHeader, @RequestBody Comment comment) {
        return userServices.updatecmnt(requestTokenHeader, comment);
    }


    //<---------------------------------------------------------User operations for Cities,Rest,Attr--------------------------------------------------->

    @RequestMapping(value = "/getallcities", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getcities() {
        return userServices.getcities();
    }

    @RequestMapping(value = "/getcitynames", method = RequestMethod.GET)
    public ResponseEntity<?> getcitynames() {
        return userServices.getCityNames();
    }

    @RequestMapping(value = "/getcity/{city}", method = RequestMethod.GET)
    public ResponseEntity<City> getcities(@PathVariable("city") String city) {
        return userServices.getcity(city);
    }

    //Getting Restaurants Api
    @RequestMapping(value = "/getrest/{city}", method = RequestMethod.GET)
    public ResponseEntity<List<Restaurant>> getRestaurants(@PathVariable("city") String city) {
        return userServices.getRestaurants(city);
    }

    //Getting Attractions Api
    @RequestMapping(value = "/getattr/{city}", method = RequestMethod.GET)
    public ResponseEntity<List<Attractions>> getattr(@PathVariable("city") String city) {
        return userServices.getattr(city);
    }

    @RequestMapping(value = "/city/{city}", method = RequestMethod.GET)
    public ResponseEntity<?> getmcity(@PathVariable("city") String city) {
        return userServices.getmcity(city);
    }

    //<---------------------------------------------------------User Details------------------------------------------------------------------------------>
    //getting user details Api
    @RequestMapping(value = "/getuserdetails", method = RequestMethod.GET)
    public ResponseEntity<?> getuserdetails(@RequestHeader("Authorization") String requestToken) {
        return userServices.getuserdetails(requestToken);
    }

    //get user username and password
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResponseEntity<?> getusername(@RequestHeader("Authorization") String requestToken) {
        return userServices.getusername(requestToken);
    }

    //-------------------------------------------------------Upvote,DownVote----------------------------------------------------------------------------->
    @RequestMapping(value = "/upvote/{ansid}", method = RequestMethod.PUT)
    public ResponseEntity<?> upvote(@RequestHeader("Authorization") String requestToken, @PathVariable("ansid") int ansid) {
        return userServices.upvote(requestToken, ansid);
    }

    @RequestMapping(value = "/downvote/{ansid}", method = RequestMethod.PUT)
    public ResponseEntity<?> downvote(@RequestHeader("Authorization") String requestToken, @PathVariable("ansid") int ansid) {
        return userServices.downvote(requestToken, ansid);
    }

    @RequestMapping(value = "/checkvote/{ansid}", method = RequestMethod.GET)
    public ResponseEntity<?> check(@RequestHeader("Authorization") String requestToken, @PathVariable("ansid") int ansid) {
        return userServices.check(requestToken, ansid);
    }


    //--------------------------------------------------------Get Similar Questions---------------------------------------------------------------------- >

    @RequestMapping(value = "/getsimques/{city}/{query}", method = RequestMethod.GET)
    public ResponseEntity<?> getuserdetails(@PathVariable("city") String city, @PathVariable("query") String query) {
        return userServices.getsimilarques(query, city);
    }




    //------------------------------------------------------------------Uplaod Image-------------------------------------------------------------------->

    @RequestMapping(method = RequestMethod.POST, value = "/imageUpload")
    public String uploadFile(@RequestParam("image") MultipartFile fileStream)
            throws IOException, ServletException {

        String bucketName = "may-cityguide";
        checkFileExtension(fileStream.getName());
        String fileName = fileStream.getName() ;

        File file = multipartToFile( fileStream,fileName) ;

        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(bucketName, fileName)
                                .build(),
                                file.toURL().openStream()
                );
        System.out.println(blobInfo.getMediaLink());
        return blobInfo.getMediaLink();
    }


//    private File convertMultiPartToFile(MultipartFile file ) throws IOException
//    {
//        File convFile = new File( file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream( convFile );
//        fos.write( file.getBytes() );
//        fos.close();
//        return convFile;
//    }
public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
    multipart.transferTo(convFile);
    return convFile;
}


    private void checkFileExtension(String fileName) throws ServletException {
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            String[] allowedExt = {".jpg", ".jpeg", ".png", ".gif"};
            for (String ext : allowedExt) {
                if (fileName.endsWith(ext)) {
                    return;
                }
            }
            throw new ServletException("file must be an image");
        }
    }
}


package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    String username;
    String password;
    String role=Role.USER;
    String emailid;
    String mob_no;
    String name;

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "username",referencedColumnName = "username")
    List<Question> questionList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "username",referencedColumnName = "username")
    List<Answer> answerList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "username",referencedColumnName = "username")
    List<Comment> commentList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "username",referencedColumnName = "username")
    List<BookMarks> bookMarksList=new ArrayList<>();
}

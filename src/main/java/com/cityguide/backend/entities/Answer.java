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
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ans_id;


    @Column(columnDefinition = "TEXT")
    String description;

    int freq=0;
    int upvotes=0;
    int downvotes=0;

    @Column(name = "ques_id")
    int ques_id;

    @Column(name="username")
    String username;

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "ans_id",referencedColumnName = "ans_id")
    List<Comment> commentList=new ArrayList<>();
}

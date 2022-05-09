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
    String description;
    int freq;
    int upvotes;
    int downvotes;

    @Column(name = "ques_id")
    int ques_id;

    @Column(name="username")
    String username;

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "ans_id",referencedColumnName = "ans_id")
    List<Comment> commentList=new ArrayList<>();


}

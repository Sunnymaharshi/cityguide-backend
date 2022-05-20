package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ques_id;
    String description;

    String category;
    int freq=0;

    @Column(name = "username")
    String username;

    @Column(name = "city_name")
    String city_name;

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "ques_id",referencedColumnName = "ques_id")
    List<Answer> answerList=new ArrayList<>();
}

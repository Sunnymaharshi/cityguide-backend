package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int comm_id;

    @Column(columnDefinition = "TEXT")
    String description;

    @Column(name = "ans_id")
    int ans_id;

    @Column(name = "username")
    String username;

}

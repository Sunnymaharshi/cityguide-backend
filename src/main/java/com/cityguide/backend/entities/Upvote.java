package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"username", "ans_id"})
})
public class Upvote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int up_id;

    String username;

    int ans_id;
}

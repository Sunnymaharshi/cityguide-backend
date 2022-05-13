package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"username", "ans_id"})
})
public class Downvote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int down_id;

    String username;

    int ans_id;
}

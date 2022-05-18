package com.cityguide.backend.CustomResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class mAnswer {
    int ans_id;
    String description;
    int freq=0;
    int upvotes=0;
    int downvotes=0;
    int ques_id;
    String username;
}

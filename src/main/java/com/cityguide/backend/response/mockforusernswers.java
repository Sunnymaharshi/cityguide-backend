package com.cityguide.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class mockforusernswers {
    int ans_id;
    String description;
    int freq=0;
    int upvotes=0;
    int downvotes=0;
    int ques_id;
    String ques_desc;
    String username;
}

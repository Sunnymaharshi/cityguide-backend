package com.cityguide.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class mQuestion {
    int ques_id;
    String description;
    String username;
    String city_name;
}

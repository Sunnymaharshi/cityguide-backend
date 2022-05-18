package com.cityguide.backend.CustomResponses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class checkvotes {
    boolean hasupvoted=false;
    boolean hasdownvoted=false;
}

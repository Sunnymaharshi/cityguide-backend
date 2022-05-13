package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int res_id;
    String res_name;
    String res_location;

    @Column(name = "city_name")
    String city_name;
}

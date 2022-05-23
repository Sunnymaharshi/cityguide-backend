package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attractions {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int attr_id;
    String attr_name;
    String filename;


    @Column(columnDefinition = "TEXT")
    String description;

    String attr_loc;

    @Column(columnDefinition = "TEXT")
    String attr_img;

    @Column(name = "city_name")
    String city_name;
}

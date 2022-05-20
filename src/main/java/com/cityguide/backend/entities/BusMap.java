package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BusMap {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int busmap_id;

    String busmap_img;
    String description;

    @Column(name = "city_name")
    String city_name;

}

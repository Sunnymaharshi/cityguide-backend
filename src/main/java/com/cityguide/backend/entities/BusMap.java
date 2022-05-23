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

    @Column(columnDefinition = "TEXT")
    String busmap_img;

    @Column(columnDefinition = "TEXT")
    String description;
    String filename;

    @Column(name = "city_name")
    String city_name;

}

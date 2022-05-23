package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MetroMap {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int metromap_id;
    String description;
    String metromap_img;
    String filename;

    @Column(name = "city_name")
    String city_name;
}

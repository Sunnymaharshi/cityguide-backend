package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int bus_id;

    @Column(columnDefinition = "TEXT")
    String bus_codes;

    @Column(columnDefinition = "TEXT")
    String bus_routes;

    String source;

    String destination;

    @Column(name = "city_name")
    String city_name;
}

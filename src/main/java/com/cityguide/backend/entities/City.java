package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class City {

    @Id
    String city_name;

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "city_name",referencedColumnName = "city_name")
    List<Restaurant> restaurantList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "city_name",referencedColumnName = "city_name")
    List<Attractions> attractionsList=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL) @JoinColumn(name = "city_name",referencedColumnName = "city_name")
    List<Question> questionList=new ArrayList<>();
}

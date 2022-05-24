package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Images {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int image_id;

    String type;

    String type_id;

    String filename;

    @Column(columnDefinition = "TEXT")
    String img_url;
}

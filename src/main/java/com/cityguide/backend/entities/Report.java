package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Report
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int report_id;
    String report_type;
    int report_type_id;
    String report_desc;
}

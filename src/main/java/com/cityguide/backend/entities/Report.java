package com.cityguide.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"report_type", "report_type_id"})
})
public class Report
{
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int report_id;
    String report_type;
    int report_type_id;
    String report_desc;

}

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
        @UniqueConstraint(columnNames = {"book_type", "book_type_id","username"})
})
public class BookMarks {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    int book_id;
    String book_type;
    int book_type_id;

    @Column(name = "username")
    String username;
}

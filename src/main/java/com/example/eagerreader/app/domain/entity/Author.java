package com.example.eagerreader.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String firstname;

    @Column(nullable=false)
    private String lastname;

    @Column(nullable=true, length = 3000)
    private String info;

//    todo add birthday

    public Author(String firstname, String lastname, String info) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.info = info;
    }
}

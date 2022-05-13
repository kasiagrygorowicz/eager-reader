package com.example.eagerreader.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author(String firstname, String lastname, String info) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.info = info;
    }

    public Author(String firstname, String lastname, String info, List<Book> books) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.info = info;
        this.books = books;
    }
}

package com.example.eagerreader.app.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Bookstore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=150)
    private String name;

    @Column
    private String link;

    @ManyToMany(mappedBy = "stores")
    private List<Book> books;
}

package com.example.eagerreader.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String location;

    @Column(nullable=true)
    private Long founded;

    @OneToMany(
            mappedBy = "publisher",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Book> books;



    public Publisher(String name, String location, Long founded) {
        this.name = name;
        this.location = location;
        this.founded = founded;
        this.books=new ArrayList<>();
    }
}

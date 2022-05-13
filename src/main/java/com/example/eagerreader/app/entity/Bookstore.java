package com.example.eagerreader.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Bookstore(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public void removeBookstore(){
        books.stream().forEach((b)->{
            b.getStores().remove(this);
        });

    }
}

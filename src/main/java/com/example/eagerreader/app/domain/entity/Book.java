package com.example.eagerreader.app.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=true, length=3000)
    private String description;

    @Column(nullable = true)
    private Long published;

    @Column(nullable = true)
    private Long reviewScore;

    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_availability",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "bookstore_id"))
    private List<Bookstore> stores;

    @ManyToMany(mappedBy = "favorites", cascade = CascadeType.ALL)
    private List<User> fans = new ArrayList<>();

//
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Review> reviews= new ArrayList<>();

    public Book(String title, String description, Long published, Long reviewScore, Publisher publisher, List<Author> authors) {
        this.title = title;
        this.description = description;
        this.published = published;
        this.reviewScore = reviewScore;
        this.publisher = publisher;
        this.authors = authors;
    }

    public Book(String title, String description, Long published, Publisher publisher, List<Author> authors) {
        this.title = title;
        this.description = description;
        this.published = published;
        this.publisher = publisher;
        this.authors = authors;
    }
}

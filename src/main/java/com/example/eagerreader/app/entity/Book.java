package com.example.eagerreader.app.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode
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

    @ManyToMany
    @Fetch(FetchMode.JOIN)
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

    @ManyToMany(mappedBy = "favorites",cascade = CascadeType.DETACH)
    private List<User> fans = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Review> reviews= new ArrayList<>();

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

    public void addRating(Long rating){
        this.reviewScore+=rating;

    }


    public void deleteFavorites(){
        List<User> users =this.fans;
        for(User u:users)
            u.getFavorites().remove(this);

    }

    public void deleteFan(User user){
        this.fans.remove(user);
    }

}

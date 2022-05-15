package com.example.eagerreader.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data

@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false)
    private Long reviewScore;

    @Column(length=1500)
    private String review;

    @Column
    private Date lastEdited;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name="book_id", nullable=false)
    private Book book;

    public Review(Long reviewScore, String review, User reviewAuthor, Book book, Date lastEdited) {
        this.reviewScore = reviewScore;
        this.review = review;
        this.author = reviewAuthor;
        this.book = book;
        this.lastEdited=lastEdited;
    }
}

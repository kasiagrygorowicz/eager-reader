package com.example.eagerreader.app.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false)
    private Long reviewScore;

    @Column(length=1500)
    private String review;

    @ManyToOne
    private User reviewAuthor;



    @ManyToOne
    private Book book;
}

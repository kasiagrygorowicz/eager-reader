package com.example.eagerreader.domain.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table
public class User{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column
    private String email;

    @NotNull
    @Column(length =50)
    private String firstname;

    @NotNull
    @Column(length=50)
    private String lastname;

//    todo favorite books list

//    todo add my ratings


}
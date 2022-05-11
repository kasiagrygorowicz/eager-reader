package com.example.eagerreader.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/book")
public class BookController {

    @GetMapping("/add")
    public String displayAddNewBookForm(){
        return "books/add-book";
    }

    @GetMapping("/edit")
    public String displayEditBookForm(){
        return "books/edit-book";
    }
}

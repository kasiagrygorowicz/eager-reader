package com.example.eagerreader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
public class BookController {

    @GetMapping("book/add")
    public String displayAddNewBookForm(){
        return "books/add-book";
    }
}

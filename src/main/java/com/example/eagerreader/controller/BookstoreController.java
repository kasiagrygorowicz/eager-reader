package com.example.eagerreader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookstore")
public class BookstoreController {

    @GetMapping("/add")
    public String displayAddNewBookstoreForm(){
        return "bookstores/add-bookstore";
    }

    @GetMapping("/edit")
    public String displayEditBookstoreForm(){
        return "bookstores/edit-bookstore";
    }
}

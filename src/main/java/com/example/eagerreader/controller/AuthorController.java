package com.example.eagerreader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @GetMapping("/add")
    public String displayAddNewAuthorForm(){
        return "authors/add-author";
    }

}

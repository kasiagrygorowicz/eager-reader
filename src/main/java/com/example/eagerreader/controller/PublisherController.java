package com.example.eagerreader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publisher")
public class PublisherController {

    @GetMapping("/add")
    public String displayAddNewPublisherForm(){
        return "publishers/add-publisher";
    }

    @GetMapping("/edit")
    public String displayEditPublisherForm(){
        return "publishers/edit-publisher";
    }
}

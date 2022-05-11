package com.example.eagerreader.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/release")
public class ReleaseController {

    @GetMapping("/add")
    public String displayAddNewReleaseForm(){
        return "releases/add-release";
    }

    @GetMapping("/edit")
    public String displayEditReleaseForm(){
        return "releases/edit-release";
    }
}

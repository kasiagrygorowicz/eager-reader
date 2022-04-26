package com.example.eagerreader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {


    @GetMapping("/")
    public String displayMainPage(){
        return "dashboard/main-page";
    }

    @GetMapping("/signup")
    public String displaySignupPage(){
        return "user/registration";
    }

    @GetMapping("/signin")
    public String displaySigninPage(){
        return "user/login";
    }

    @GetMapping("/books/*")
    public String displayBooksPage(){
        return "dashboard/all";
    }
}

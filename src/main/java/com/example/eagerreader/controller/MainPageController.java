package com.example.eagerreader.controller;

import com.example.eagerreader.dto.CreateUserDto;
import com.example.eagerreader.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final UserService userService;


    @GetMapping("/")
    public String displayMainPage(){
        return "dashboard/main-page";
    }

    @GetMapping("/signup")
    public String displaySignupPage(Model model){
        model.addAttribute("newUser", new CreateUserDto());
        return "user/registration";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute CreateUserDto newUser, RedirectAttributes attributes){
       userService.addUser(newUser);

//       todo add handling errors
       attributes.addFlashAttribute("success","New account registered successfully");
       return "redirect:/signin";
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

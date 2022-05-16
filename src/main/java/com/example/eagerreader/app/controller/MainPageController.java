package com.example.eagerreader.app.controller;

import com.example.eagerreader.app.dto.CreateUserDto;
import com.example.eagerreader.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/")
public class MainPageController {

    private final UserService userService;


    @GetMapping("/")
    public String displayMainPage(){
        return "dashboard/main-page";
    }

    @GetMapping("/signup")
    public String displaySignupPage(Model model){
        model.addAttribute("newUser", new CreateUserDto());
        return "forms/user/registration";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("newUser") CreateUserDto newUser,BindingResult bindingResult, RedirectAttributes attributes){
        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return "forms/user/registration";
        }else {
            try {
                userService.addUser(newUser);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                return "forms/user/registration";
            }

//       todo add handling errors
            attributes.addFlashAttribute("success", "New account registered successfully");
            return "redirect:/signin";
        }
    }

    @GetMapping("/signin")
    public String displaySigninPage(){
        return "forms/user/login";
    }

    @GetMapping("/books/*")
    public String displayBooksPage(){
        return "dashboard/all";
    }
}

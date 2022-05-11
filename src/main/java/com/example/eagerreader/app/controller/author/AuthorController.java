package com.example.eagerreader.app.controller.author;

import com.example.eagerreader.app.domain.repository.AuthorRepository;
import com.example.eagerreader.app.service.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/all")
    public String displayAllAuthorsPage(Model model){
        model.addAttribute("authors",authorService.getAllAuthors());
        return "pages/authors/all-authors";
    }

    @GetMapping("/{id}/details")
    public String displayAuthorDetailsPage(Model model){
        model.addAttribute("authors",authorService.getAllAuthors());
        return "pages/authors/author-details";
    }
}

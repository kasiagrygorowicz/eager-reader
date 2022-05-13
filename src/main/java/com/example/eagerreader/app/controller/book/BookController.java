package com.example.eagerreader.app.controller.book;

import com.example.eagerreader.app.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String displayAllBooks(Model model, @RequestParam String filter, RedirectAttributes attributes){
        try {
            if (filter.equals("all")) model.addAttribute("books", bookService.getAllBooks());
            else model.addAttribute("books", bookService.getAllBooksByFilter(filter));
        }catch(RuntimeException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books?filter=all";
        }
        return "pages/books/all-books";
    }


}

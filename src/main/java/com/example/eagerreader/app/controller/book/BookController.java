package com.example.eagerreader.app.controller.book;

import com.example.eagerreader.app.dto.author.AuthorDetailsDTO;
import com.example.eagerreader.app.dto.author.AuthorFilter;
import com.example.eagerreader.app.dto.publisher.CreatePublisherDTO;
import com.example.eagerreader.app.dto.review.CreateReviewDTO;
import com.example.eagerreader.app.entity.Author;
import com.example.eagerreader.app.exception.bookException.BookNotFoundException;
import com.example.eagerreader.app.service.author.AuthorService;
import com.example.eagerreader.app.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

//    == get all books ==
    @GetMapping
    public String displayAllBooks(Model model, @RequestParam String filter,@RequestParam Long authorId, RedirectAttributes attributes){
        try {
            if (filter.equals("all")) model.addAttribute("books", bookService.getAllBooks());
            else model.addAttribute("books", bookService.getAllBooksByFilter(filter));
        }catch(RuntimeException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books?filter=all";
        }
        model.addAttribute("authors",authorService.getAllAuthors());
        AuthorDetailsDTO author = authorService.getAuthorDetails(authorId);
        model.addAttribute("filterAuthor",new AuthorFilter(author.getFirstname(), author.getLastname(), authorId.toString()));
        model.addAttribute("filter",new String(filter));
        return "redirect:/books?filter="+filter+"&authorId="+authorId;
    }

//    == get book details ==
    @GetMapping("/book/{id}")
    public String displayBookDetails(@PathVariable Long id, Model model,RedirectAttributes attributes){
        try {
            model.addAttribute("book", bookService.getBookDetails(id));
            model.addAttribute("review", new CreateReviewDTO());
            model.addAttribute("reviews",bookService.getReviews(id));
            bookService.getReviews(id).stream().forEach(
                    (e)->System.out.println(e.getRating())
            );
        }catch(BookNotFoundException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books?filter=all";
        }

        return "/pages/books/book-details";

    }


}

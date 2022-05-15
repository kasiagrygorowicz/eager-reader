package com.example.eagerreader.app.controller.book;

import com.example.eagerreader.app.dto.book.BookDetailsDTO;
import com.example.eagerreader.app.dto.book.CreateBookDTO;
import com.example.eagerreader.app.dto.book.EditBookDTO;
import com.example.eagerreader.app.dto.bookstore.BookstoreDTO;
import com.example.eagerreader.app.dto.publisher.PublisherDTO;
import com.example.eagerreader.app.exception.authorException.author.AuthorNotFoundException;
import com.example.eagerreader.app.exception.authorException.author.DuplicateAuthorException;
import com.example.eagerreader.app.exception.bookException.BookNotFoundException;
import com.example.eagerreader.app.service.author.AuthorService;
import com.example.eagerreader.app.service.book.BookService;
import com.example.eagerreader.app.service.bookstore.BookstoreService;
import com.example.eagerreader.app.service.publisher.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
//@PreAuthorize("hasAuthority('admin')")
public class PrivilegedBookController {

    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookService bookService;
    private final BookstoreService bookstoreService;

//    == add book ==

    @GetMapping("/add")
    public String displayAddNewBookForm(Model model) {
        model.addAttribute("book", new CreateBookDTO());
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("publishers", publisherService.getAllPublishers());
        return "forms/books/add-book";
    }

    @PostMapping("/add")
    public String addNewBook(@ModelAttribute("book") CreateBookDTO book, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return "forms/authors/edit-author";
        try {
            bookService.addBook(book);
        } catch (DuplicateAuthorException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/book/add";
        }
        attributes.addFlashAttribute("success", "New book added successfully");
        return "redirect:/books?filter=all";
    }

//    == edit book ==

    @GetMapping("/edit/{id}")
    public String displayEditBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookToEdit(id));
        String b =null;
        for(BookstoreDTO bk:bookstoreService.getAllBookstores())
            b+=bk.getName()+", ";
        model.addAttribute("bookstores", b);
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("publishers", publisherService.getAllPublishers());
        return "forms/books/edit-book";
    }

    @PutMapping("/edit/{id}")
    public String displayEditBookForm(@PathVariable Long id, @Valid @ModelAttribute("book")EditBookDTO book,BindingResult bindingResult,RedirectAttributes attributes) {
        if (bindingResult.hasErrors())  return "redirect:/book/edit/"+id;
        try {
            bookService.editBook(book, id);
        }catch(RuntimeException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/book/edit/"+id;
        }
        return "redirect:/books?filter=all";
    }

//    delete
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes attributes){
        try{
            bookService.deleteBook(id);
        }catch(BookNotFoundException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books?filter=all";
        }
        attributes.addFlashAttribute("success", "Book deleted successfully");
        return "redirect:/books?filter=all";
    }
}

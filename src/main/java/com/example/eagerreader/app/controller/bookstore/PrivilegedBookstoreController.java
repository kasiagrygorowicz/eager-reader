package com.example.eagerreader.app.controller.bookstore;

import com.example.eagerreader.app.dto.bookstore.CreateBookstoreDTO;
import com.example.eagerreader.app.exception.authorException.author.DuplicateAuthorException;
import com.example.eagerreader.app.exception.bookException.BookNotFoundException;
import com.example.eagerreader.app.exception.bookstoreException.DuplicateBookstoreException;
import com.example.eagerreader.app.service.bookstore.BookstoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.nio.file.Path;

@RequiredArgsConstructor
@PreAuthorize("hasAuthority('admin')")
@Controller
@RequestMapping("/bookstore")
public class PrivilegedBookstoreController {

    private final BookstoreService bookstoreService;

    //    == get all bookstores ==
    @GetMapping("/all")
    public String displayAllBookstores(Model model) {
        model.addAttribute("bookstores", bookstoreService.getAllBookstores());
        return "pages/bookstores/all-bookstores";

    }

    //    == add bookstore ==
    @GetMapping("/add")
    public String displayAddNewBookstoreForm(Model model) {
        model.addAttribute("bookstore", new CreateBookstoreDTO());
        return "forms/bookstores/add-bookstore";
    }

    @PostMapping("/add")
    public String addNewBookstore(@ModelAttribute("bookstore") CreateBookstoreDTO bookstore, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return "forms/bookstores/add-bookstore";
        try {
            bookstoreService.addBookstore(bookstore);
        } catch (DuplicateBookstoreException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookstore/add";
        }

        attributes.addFlashAttribute("success", "New author added successfully");
        return "redirect:/bookstore/all";
    }

//    == edit bookstore ==

    @GetMapping("/edit/{id}")
    public String displayEditBookstoreForm(Model model, @PathVariable Long id, RedirectAttributes attributes) {
        try {
            model.addAttribute("bookstore", bookstoreService.getBookstoreToEdit(id));
        } catch (BookNotFoundException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookstore/all/";
        }

        return "forms/bookstores/edit-bookstore";
    }

    @PutMapping("/edit/{id}")
    public String displayEditBookstoreForm(@PathVariable Long id, @Valid @ModelAttribute("bookstore") CreateBookstoreDTO bookstore, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return "forms/bookstores/edit-bookstore";
        try {
            bookstoreService.editBookstore(bookstore, id);
        } catch (DuplicateBookstoreException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookstore/edit/" + id;
        }

        attributes.addFlashAttribute("success", "New author added successfully");
        return "redirect:/bookstore/all";
    }

    //   ==  delete bookstore ==
    @GetMapping("delete/{id}")
    public String deleteBookstoreById(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            bookstoreService.deleteBookstore(id);
        } catch (BookNotFoundException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookstore/all";
        }

        attributes.addFlashAttribute("success", "Bookstore with id " + id + " deleted successfully");
        return "redirect:/bookstore/all";
    }


}

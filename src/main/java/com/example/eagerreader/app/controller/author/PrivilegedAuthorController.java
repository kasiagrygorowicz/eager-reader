package com.example.eagerreader.app.controller.author;

import com.example.eagerreader.app.dto.author.CreateAuthorDTO;
import com.example.eagerreader.app.dto.author.EditAuthorDTO;
import com.example.eagerreader.app.exception.authorException.author.AuthorNotFoundException;
import com.example.eagerreader.app.exception.authorException.author.DuplicateAuthorException;
import com.example.eagerreader.app.service.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/author")
@PreAuthorize("hasAuthority('admin')")
@RequiredArgsConstructor
public class PrivilegedAuthorController {

    private final AuthorService authorService;
    private final Logger log = LoggerFactory.getLogger(PrivilegedAuthorController.class);

//    == add publisher ==

    @GetMapping("/add")
    public String displayAddNewAuthorForm(Model model) {
        model.addAttribute("author", new CreateAuthorDTO());
        return "forms/authors/add-author";
    }

    @PostMapping("/add")
    public String addNewAuthor(@Valid @ModelAttribute("author") CreateAuthorDTO author, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return "forms/authors/add-author";
        try {
            authorService.addAuthor(author);
        } catch (DuplicateAuthorException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/author/add";
        }

        attributes.addFlashAttribute("success", "New publisher added successfully");
        return "redirect:pages/authors/all-authors";
    }

//    == edit publisher ==

    @GetMapping("/edit/{id}")
    public String displayEditAuthorForm(Model model, @PathVariable Long id) {
        model.addAttribute("author", authorService.getAuthorToEdit(id));
        return "forms/authors/edit-author";
    }

    @PutMapping("/edit/{id}")
    public String editAuthor(@Valid @ModelAttribute("author") EditAuthorDTO author, @PathVariable Long id, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return "forms/authors/edit-author";
        try {
            authorService.editAuthor(author,id);
        }catch(DuplicateAuthorException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/author/edit/"+id;
        }catch(AuthorNotFoundException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/authors/all";
        }
        attributes.addFlashAttribute("success", "Author edited successfully");
        return "redirect:/authors/all";
    }

//    == delete publisher ==
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id){
        System.out.println("deleting");
        authorService.deleteAuthor(id);
        return "redirect:/authors/all";
    }


}

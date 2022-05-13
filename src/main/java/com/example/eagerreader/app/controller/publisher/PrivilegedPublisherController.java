package com.example.eagerreader.app.controller.publisher;

import com.example.eagerreader.app.dto.author.EditAuthorDTO;
import com.example.eagerreader.app.dto.publisher.CreatePublisherDTO;
import com.example.eagerreader.app.dto.publisher.EditPublisherDTO;
import com.example.eagerreader.app.exception.authorException.author.AuthorNotFoundException;
import com.example.eagerreader.app.exception.authorException.author.DuplicateAuthorException;
import com.example.eagerreader.app.exception.authorException.publisher.DuplicatePublisherException;
import com.example.eagerreader.app.exception.authorException.publisher.PublisherNotFoundException;
import com.example.eagerreader.app.service.publisher.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/publisher")
@RequiredArgsConstructor
//todo secure
//@PreAuthorize("hasAuthority('admin')")
public class PrivilegedPublisherController {

    private final PublisherService publisherService;


//    == add publisher ==
    @GetMapping("/add")
    public String displayAddNewPublisherForm(Model model){
        model.addAttribute("publisher",new CreatePublisherDTO());
        return "forms/publishers/add-publisher";
    }

    @PostMapping("/add")
    public String addNewPublisher(@Valid @ModelAttribute("publisher") CreatePublisherDTO publisher, BindingResult bindingResult, RedirectAttributes attributes ){
        if (bindingResult.hasErrors()) return "forms/publishers/add-publisher";
        try {
            publisherService.addPublisher(publisher);
        }catch(DuplicatePublisherException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/publisher/add";
        }
        attributes.addFlashAttribute("success", "New publisher added successfully");
        return "redirect:/publishers/all";
    }

//    == edit publisher ==
    @GetMapping("/edit/{id}")
    public String displayEditPublisherForm(@PathVariable Long id, Model model){
        model.addAttribute("publisher",publisherService.getPublisherToEdit(id));
        return "forms/publishers/edit-publisher";
    }

    @PutMapping("/edit/{id}")
    public String editPublisher(@Valid @ModelAttribute("publisher") EditPublisherDTO publisher, @PathVariable Long id, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return "forms/publishers/edit-publisher";
        try {
            publisherService.editPublisher(publisher,id);
        }catch(DuplicatePublisherException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/publisher/edit/"+id;
        }catch(PublisherNotFoundException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/publishers/all";
        }
        attributes.addFlashAttribute("success", "Publisher edited successfully");
        return "redirect:/publishers/all";
    }

    //    == delete publisher ==
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id,RedirectAttributes attributes){
        publisherService.deletePublisher(id);
        attributes.addFlashAttribute("success", "Publisher deleted successfully");
        return "redirect:/publishers/all";
    }

}

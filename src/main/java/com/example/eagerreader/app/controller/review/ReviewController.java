package com.example.eagerreader.app.controller.review;

import com.example.eagerreader.app.dto.review.CreateReviewDTO;
import com.example.eagerreader.app.exception.bookException.BookNotFoundException;
import com.example.eagerreader.app.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/book/review")
@Controller
public class ReviewController {

    private final ReviewService reviewService;


//    == add review ==
    @PostMapping("/{id}")
    public String addReview(@PathVariable Long id, @Valid @ModelAttribute("review")CreateReviewDTO review, BindingResult bindingResult, RedirectAttributes attributes){
        if (bindingResult.hasErrors()) return "redirect:/books/book/"+id;
        try{
            reviewService.addNewReview(review,id);
        }catch(BookNotFoundException e){
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books/book/"+id;
        }

        attributes.addFlashAttribute("success", "Review added");
        return "redirect:/books/book/"+id;

    }
}

package com.example.eagerreader.app.controller.user;

import com.example.eagerreader.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("{id}/favorite/add")
    public String addToFavorite(@PathVariable Long id){
        System.out.println("posting adding to favorite");
        userService.addToFavorites(id);
        return "redirect:/books/book/"+id;
    }

    @PostMapping("/favorite/delete")
    public String deleteFromFavorite(@RequestParam Long id){
        System.out.println("posting deleting to favorite");
        userService.deleteFromFavorites(id);
        return "redirect:/books/book/"+id;
    }

    @GetMapping("favorites")
    public String displayFavoriteBooks(Model model){
        model.addAttribute("favs",userService.getFavorites());
        return "pages/user/favs";
    }

}

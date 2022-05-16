package com.example.eagerreader.app.controller.user;

import com.example.eagerreader.app.entity.Author;
import com.example.eagerreader.app.entity.User;
import com.example.eagerreader.app.repository.AuthorRepository;
import com.example.eagerreader.app.service.export.ExportService;
import com.example.eagerreader.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final ExportService exportService;
    private final AuthorRepository authorRepository;

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



    @GetMapping("/favorites/export")
    public ResponseEntity<Resource> downloadFavorites() throws IOException {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        exportService.exportFavoriteBooks();
        File file = getFile("favoriteBooks-"+user.getEmail());
        return sendReport(file);
    }

    @GetMapping("/author/export/{id}")
    public ResponseEntity<Resource> downloadAuthorBook(@PathVariable Long id) throws IOException {
        exportService.exportAuthorBooks(id);
        Author author =authorRepository.getById(id);
        File file = getFile("books-of-"+author.getFirstname()+"-"+author.getLastname());
        return sendReport(file);
    }


    private ResponseEntity<Resource> sendReport(File file) throws IOException {
        InputStreamResource resource = exportService.readReport(file);

        HttpHeaders headers = new HttpHeaders();
        addHeaders(headers, file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private void addHeaders(HttpHeaders headers, String fileName) {
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
    }

    private File getFile(String name) {
        return new File(getClass().getClassLoader().getResource(name+".xlsx").getFile());
    }

}

package com.example.eagerreader.app.controller.publisher;

import com.example.eagerreader.app.service.publisher.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publishers")
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping("/all")
    public String displayAllPublishersPage(Model model){
        model.addAttribute("publishers",publisherService.getAllPublishers());
        return "/pages/publisher/all-publishers";
    }
}

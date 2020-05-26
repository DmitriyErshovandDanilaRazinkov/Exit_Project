package com.controller;

import com.service.AudioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class MainController {

    private AudioService audioService;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("morePopular", audioService.getTop(10));
        return "mainPage";
    }
}

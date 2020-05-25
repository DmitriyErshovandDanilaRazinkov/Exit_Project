package com.controller;

import com.model.DTO.user.AudioPageTo;
import com.service.AudioService;
import com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class MainController {

    //private AudioService audioService;

    @GetMapping("/")
    public String mainPage() {
        //AudioPageTo audioPageTo = new AudioPageTo();
       // model.addAttribute("pageTo", pageTo);
        return "mainPage";
    }
}

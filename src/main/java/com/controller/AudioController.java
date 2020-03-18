package com.controller;

import com.repository.AudioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class AudioController {

    AudioRepository repository;

    public AudioController(AudioRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/audio")
    public String getAllAudio(Map<String, Object> model){

        model.put("audios", repository.findAll());

        return "listAudios";
    }
}

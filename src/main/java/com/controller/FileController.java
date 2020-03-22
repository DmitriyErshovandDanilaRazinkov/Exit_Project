package com.controller;

import com.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    private FileRepository repository;

    public FileController(FileRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/files")
    public String getAllAudio(Map<String, Object> model){

        model.put("files", repository.findAll());

        return "files/listFiles1";
    }

}

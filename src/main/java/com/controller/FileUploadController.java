package com.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static java.util.UUID.randomUUID;

@Controller
public class FileUploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/files")
    public String get() {
        return "uploadForm";
    }

    @PostMapping("/files")
    public String add(@RequestParam("file")MultipartFile file) throws IOException {
        if(file != null) {
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" +  resultFileName));
        }

        return "uploadForm";
    }
}

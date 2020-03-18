package com.controller;

import com.model.Audio;
import com.repository.AudioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.util.UUID.randomUUID;

@Controller
public class FileUploadController {

    @Value("${upload.path}")
    private String uploadPath;

    private AudioRepository repository;

    public FileUploadController(AudioRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/files")
    public String get() {
        return "uploadForm";
    }

    @PostMapping("/files")
    public String uploadFile(@RequestParam String name, @RequestParam String tag,
                             @RequestParam("file")MultipartFile file, Map<String, Object> model) throws IOException {
        if(file != null) {
            File uploadDir = new File(uploadPath);

            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" +  resultFileName));

            Audio newAudio = new Audio(name, tag, uploadPath + "/" + resultFileName);

            repository.save(newAudio);

            model.put("result", "File upload");
        }
        else
        {
            model.put("result", "File not upload");
        }

        return "uploadResult";
    }
}

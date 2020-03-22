package com.controller;

import com.model.Audio;
import com.model.FileAud;
import com.repository.AudioRepository;
import com.repository.FileRepository;
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
public class UploadController {

    @Value("${upload.path}")
    private String uploadPath;

    AudioRepository audioRepository;
    FileRepository fileRepository;

    public UploadController(AudioRepository audioRepository, FileRepository fileRepository) {
        this.audioRepository = audioRepository;
        this.fileRepository = fileRepository;
    }

    @GetMapping("/upload/audio")
    public String uploadAudioForm () {
        return "uploadForm/uploadAudio";
    }

    @PostMapping("/upload/audio")
    public String uploadAudio(@RequestParam String name, @RequestParam String tag,
                              @RequestParam("file") MultipartFile file, Map<String, Object> model) throws IOException {

        audioRepository.save(new Audio(name, tag, uploadFile(file)));
        model.put("result", "Audio upload");
        return "uploadForm/uploadResult";
    }

    @GetMapping("/upload/file")
    public String uploadFileForm () {
        return "uploadForm/uploadFile";
    }

    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam("file")MultipartFile file, Map<String, Object> model) throws IOException {
        if(uploadFile(file) != -1) {
            model.put("result", "File upload");
        }
        else
        {
            model.put("result", "File not upload");
        }

        return "uploadForm/uploadResult";
    }

    public Long uploadFile(MultipartFile file) throws IOException {
        if(file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            FileAud newFile = new FileAud(resultFileName, file.getOriginalFilename());
            fileRepository.save(newFile);
            return  newFile.getId();
        } else {
            return (long) -1;
        }
    }
}

package com.controller;

import com.service.AudioService;
import com.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Api
@Controller
@RequestMapping("/upload")
public class UploadController {

    private FileService fileService;
    private AudioService audioService;

    public UploadController(AudioService audioService, FileService fileService) {
        this.audioService = audioService;
        this.fileService = fileService;
    }


    @GetMapping("/audio")
    public String uploadAudioForm() {
        return "uploadForm/uploadAudio";
    }

    @PostMapping("/audio")
    public String uploadAudio(@RequestParam String name,
                              @RequestParam("file") MultipartFile file, Map<String, Object> model) throws IOException {
        audioService.uploadAudio(name, file);
        model.put("result", "File upload");
        return "uploadForm/uploadResult";
    }

    @GetMapping("/file")
    public String uploadFileForm () {
        return "uploadForm/uploadFile";
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("file")MultipartFile file, Map<String, Object> model) throws IOException {
        if (fileService.uploadFile(file) != -1) {
            model.put("result", "File upload");
        } else {
            model.put("result", "File not upload");
        }

        return "uploadForm/uploadResult";
    }

}

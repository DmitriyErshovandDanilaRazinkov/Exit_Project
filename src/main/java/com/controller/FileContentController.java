package com.controller;

import com.model.FileAud;
import com.service.AudioService;
import com.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Api
@Controller
@RolesAllowed({"USER", "ADMIN"})
public class FileContentController {

    @Value("${upload.path}")
    private String uploadPath;

    private ServletContext servletContext;

    private FileService fileService;

    private AudioService audioService;

    public FileContentController(ServletContext servletContext, FileService fileService, AudioService audioService) {
        this.servletContext = servletContext;
        this.fileService = fileService;
        this.audioService = audioService;
    }

    @RequestMapping("/download/{fileId}")
    public void downloadAudioResource(HttpServletRequest request, HttpServletResponse response, @PathVariable() Long fileId) throws IOException {
        String fileName = fileService.foundFileById(fileId).getName();
        File file = new File(uploadPath + '/' + fileName);

        response.setHeader("Content-Type", servletContext.getMimeType(removeExtension(fileName)));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; fileName=\"" + uploadPath + "/" + fileName);
        Files.copy(file.toPath(), response.getOutputStream());
    }

    @GetMapping("/upload/audio")
    public String uploadAudioForm() {
        return "uploadForm/uploadAudio";
    }

    @PostMapping("/upload/audio")
    public String uploadAudio(@RequestParam String name,
                              @RequestParam("file") MultipartFile file, Model model) throws IOException {
        audioService.uploadAudio(name, file);
        model.addAttribute("result", "File upload");
        return "uploadForm/uploadResult";
    }

    @GetMapping("/upload/file")
    public String uploadFileForm() {
        return "uploadForm/uploadFile";
    }

    @PostMapping("/upload/file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        fileService.uploadFile(file);
        model.addAttribute("result", "File upload");
        return "uploadForm/uploadResult";
    }


    public static String removeExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }
}

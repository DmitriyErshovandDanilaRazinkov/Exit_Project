package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("download")
public class DownloadController extends HttpServlet {

    @Value("${upload.path}")
    private String uploadPath;

    private ServletContext servletContext;

    private FileService fileService;

    public DownloadController(ServletContext servletContext, FileService fileService) {
        this.servletContext = servletContext;
        this.fileService = fileService;
    }

    @RequestMapping("/{fileId}")
    public void downloadAudioResource(HttpServletRequest request, HttpServletResponse response, @PathVariable() Long fileId) throws IOException {
        String fileName = fileService.foundFileById(fileId).getName();
        File file = new File(uploadPath + '/' + fileName);

        response.setHeader("Content-Type", servletContext.getMimeType(removeExtension(fileName)));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; fileName=\"" + uploadPath + "/" + fileName);
        Files.copy(file.toPath(), response.getOutputStream());
    }


    public static String removeExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }
}
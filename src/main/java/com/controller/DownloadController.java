package com.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import com.model.FileAud;
import com.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("download")
public class DownloadController extends HttpServlet {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private FileService fileService;

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
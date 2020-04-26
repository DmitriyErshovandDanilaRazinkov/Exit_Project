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


@RestController
@RequestMapping("admin/download")
public class DownloadController extends HttpServlet {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private FileService fileService;

    @RequestMapping("/{fileName}")
    public void downloadAudioResource(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException {
        File file = new File(uploadPath + "/" + fileName);
//            FileAud fileAud = fileService.foundFileById(id);
//            String realName = fileAud.getOriginalName();;
//            response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + realName + "\""));
//            response.setContentLength((int) file.length());
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//            FileCopyUtils.copy(inputStream, response.getOutputStream());
        response.setHeader("Content-Type", "audio/mpeg");
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
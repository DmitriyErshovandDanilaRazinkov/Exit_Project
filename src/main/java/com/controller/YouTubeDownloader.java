package com.controller;


import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;
import com.model.DTO.pages.YouTubeVideoTo;
import com.service.AudioService;
import com.service.FileService;
import org.apache.catalina.valves.HealthCheckValve;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@Controller
public class YouTubeDownloader {

    @Value("${upload.path}")
    private String uploadPath;

    private AudioService audioService;

    public YouTubeDownloader( AudioService audioService) {
        this.audioService = audioService;
    }

    YoutubeDownloader downloader = new YoutubeDownloader();

    @GetMapping ("/downloadingpage")
    public String downloadAudioFromYouTube(Model model) throws IOException, YoutubeException {

        return "/youtubeDownloading/youtubeAudio";
    }


    @PostMapping("/downloadaudiofrom")
    public String downloadAudioFromYouTube(@RequestParam("link") String link, Model model, RedirectAttributes redirectAttributes) throws IOException, YoutubeException, InterruptedException {

        String videoId = link.substring(link.indexOf('=') + 1);
        YoutubeVideo video = downloader.getVideo(videoId);
        VideoDetails videoDetails = video.details();
        System.out.println(videoDetails.title());

        List<AudioFormat> audioFormats = video.audioFormats();
        audioFormats.forEach(it -> {
            System.out.println(it.audioQuality() + ": " + it.url());
        });


        //скачка файла
        File outputDirAudio = new File(uploadPath);
        video.downloadAsync(audioFormats.get(0), outputDirAudio, new OnYoutubeDownloadListener() {
            @Override
            public void onDownloading(int progress) {
                System.out.printf("\b\b\b\b\b%d%%", progress);
            }

            @Override
            public void onFinished(File file) {
                System.out.println("Finished file: " + file);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error: " + throwable.getLocalizedMessage());
            }
        });

        //получаем список всех потоков
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        //находим поток, отвечающий за скачивание и заставляем главный поток ожидать его завершения
        for (Thread thread : threadArray) {
            if(thread.getName() == "YtDownloader") {
                thread.join();
                break;
            }
        }
        
        //конвертирование файл в мультипартфайл
        Path path = Paths.get(outputDirAudio.getPath() + "/" + videoDetails.title() + ".mp4");
        String name = videoDetails.title();
        String originalFileName = name + ".mp4";
        String contentType = "video";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        //добавление в библиотеку
        audioService.uploadAudio(videoDetails.title(), false, result);


        //Topage
        YouTubeVideoTo youTubeVideoTo = new YouTubeVideoTo(video);

        model.addAttribute("videoDetails", youTubeVideoTo);

        return "/youtubeDownloading/videoDetailsPage";
    }




    private void downloadFile(YoutubeVideo video, AudioFormat audioFormat, File outputDirAudio) throws IOException, YoutubeException, InterruptedException {
        //скачка файла
        video.downloadAsync(audioFormat, outputDirAudio, new OnYoutubeDownloadListener() {
            @Override
            public void onDownloading(int progress) {
                System.out.printf("\b\b\b\b\b%d%%", progress);
            }

            @Override
            public void onFinished(File file) {
                System.out.println("Finished file: " + file);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error: " + throwable.getLocalizedMessage());
            }
        });

        Thread.currentThread().join();
    }


    @PostMapping("/addaudiofrom")
    public String addAudioFromYouTube(String videoName, File outputDirAudio, Model model, RedirectAttributes redirectAttributes) throws IOException{
        //конвертирование файл в мультипартфайл
        Path path = Paths.get(outputDirAudio.getPath() + "/" + videoName + "mp4");
        String name = videoName;
        String originalFileName = name + "mp4";
        String contentType = "video";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        //добавление в библиотеку
        audioService.uploadAudio(videoName, false, result);


        return "/mainPage";
    }


}

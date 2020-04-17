package com.controller;

import com.service.AudioService;
import com.service.FileService;
import com.service.TagService;
import com.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Api
@Controller
public class AdminController {

    private UserService userService;

    private FileService fileService;

    private AudioService audioService;

    private TagService tagService;

    public AdminController(UserService userService, FileService fileService, AudioService audioService, TagService tagService) {
        this.userService = userService;
        this.fileService = fileService;
        this.audioService = audioService;
        this.tagService = tagService;
    }

    @ApiOperation("Главная страница админа")
    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admins/admin";
    }

    //Работа спользователями
    @ApiOperation("Показывает лист пользователей")
    @GetMapping("/admin/users")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "/users/listUsers";
    }

    @ApiOperation("Удаляет пользователя")
    @PostMapping("/admin/users")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        return userList(model);
    }

    @ApiOperation("Показывает пользователя по id")
    @GetMapping("/admin/getUser/{userId}")
    public String getUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.userGetList(userId));
        return "/users/listUsers";
    }

    //Работа с файлами
    @ApiOperation("Показывает лист файлов")
    @GetMapping("/admin/files")
    public String fileList(Model model) {
        model.addAttribute("files", fileService.getAll());
        return "/files/listFiles1";
    }

    @ApiOperation("Удаляет файл")
    @PostMapping("/admin/files")
    public String deleteFile(@RequestParam(required = true, defaultValue = "") Long fileId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            fileService.deleteFile(fileId);
        }
        return fileList(model);
    }

    @ApiOperation("Показывает файл по id")
    @GetMapping("/admin/getFile/{fileId}")
    public String getFile(@PathVariable("fileId") Long fileId, Model model) {
        model.addAttribute("files", fileService.foundFileById(fileId));
        return "/files/listFiles1";
    }

    //Работа с аудио
    @ApiOperation("Показывает все аудио")
    @GetMapping("/admin/audios")
    public String audioList(Model model) {
        model.addAttribute("audios", audioService.getAll());
        return "/audios/listAudios";
    }

    @ApiOperation("Удаляет аудио")
    @PostMapping("/admin/audios")
    public String deleteAudio(@RequestParam(required = true, defaultValue = "") Long audioId,
                              @RequestParam(required = true, defaultValue = "") String action,
                              Model model) {
        if (action.equals("delete")) {
            audioService.deleteAudio(audioId);
        }
        return audioList(model);
    }

    @ApiOperation("Аудио по id")
    @GetMapping("/admin/audio/{audioId}")
    public String getAudio(@PathVariable Long audioId, Model model) {
        try {
            model.addAttribute("audio", audioService.foundAudioById(audioId));
            model.addAttribute("tagList", tagService.getAll());
        } catch (NotFoundException e) {
            model.addAttribute("exceptionTxt", e.getMessage());
            return "/exception";
        }
        return "/audios/audio";
    }

    @ApiOperation("Добавление тэга к аудио")
    @GetMapping("/admin/audios/{audioId}/{tagId}")
    public String addTagToAudio(@PathVariable long audioId, @PathVariable long tagId, Model model) {
        try {
            audioService.addTagToAudio(audioId, tagId);
        } catch (NotFoundException e) {
            model.addAttribute("exception", e.getMessage());
            return "/exception";
        }
        return getAudio(audioId, model);
    }

    //Работа с тэгами
    @ApiOperation("Показывает лист тэгов")
    @GetMapping("/admin/tags")
    public String tagList(Model model) {
        model.addAttribute("tags", tagService.getAll());
        return "/tags/listTags";
    }

    @ApiOperation("Удаляет тэг")
    @PostMapping("/admin/tags")
    public String deleteTag(@RequestParam(required = true, defaultValue = "") Long tagId,
                            @RequestParam(required = true, defaultValue = "") String action,
                            Model model) {
        if (action.equals("delete")) {
            if (tagService.deleteTag(tagId)) {
                return "mainPage";
            }
        }
        return tagList(model);
    }

    @PostMapping("/admin/addTag")
    public String addTag(@RequestParam String name, Model model) {
        tagService.addTag(name);
        return tagList(model);
    }

    @ApiOperation("Показывает тэг по id")
    @GetMapping("/admin/getTag/{tagId}")
    public String getTag(@PathVariable("tagId") Long tagId, Model model) {
        try {
            model.addAttribute("tags", tagService.foundTagById(tagId));
        } catch (NotFoundException e) {
            model.addAttribute("exceptionTxt", e.getMessage());
            return "/exception";
        }
        return "/tags/listTags";
    }
}

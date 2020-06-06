package com.controller;

import com.model.DTO.FileTo;
import com.model.User;
import com.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

@AllArgsConstructor
@Api
@Controller
@RequestMapping("/admin")
@RolesAllowed(User.ROLE_ADMIN)
public class AdminController {

    private UserService userService;

    private FileService fileService;

    private AudioService audioService;

    private TagService tagService;

    private PlayListService playListService;

    @ApiOperation("Главная страница админа")
    @GetMapping
    public String getAdminPage() {
        return "/admins/admin";
    }

    //Работа спользователями
    @ApiOperation("Показывает лист пользователей")
    @GetMapping("/users")
    public String userList(Model model) {

        model.addAttribute("allUsers", userService.allUsers());

        return "/users/listUsers";
    }

    @ApiOperation("Удаляет пользователя")
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(defaultValue = "") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/users";
    }

    @ApiOperation("Повышает пользователя до админа")
    @PostMapping("/addToAdmin")
    public String addToAdmin(@RequestParam(defaultValue = "") Long userId) {
        userService.addToAdmin(userId);
        return "redirect:/admin/users";
    }

    @ApiOperation("Показывает пользователя по id")
    @GetMapping("/getUser/{userId}")
    public String getUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.findUserToById(userId));
        return "/users/listUsers";
    }

    //Работа с файлами
    @ApiOperation("Показывает лист файлов")
    @GetMapping("/files")
    public String fileList(Model model) {
        model.addAttribute("files", fileService.getAll());
        return "/files/listFiles1";
    }

    @ApiOperation("Удаляет файл")
    @PostMapping("/deleteFile")
    public String deleteFile(@RequestParam(defaultValue = "") Long fileId,
                             @RequestParam(defaultValue = "") String action) {
        if (action.equals("delete")) {
            fileService.deleteFile(fileId);
        }
        return "redirect:/admin/files";
    }

    @ApiOperation("Показывает файл по id")
    @GetMapping("/getFile/{fileId}")
    public String getFile(@PathVariable("fileId") Long fileId, Model model) {
        ArrayList<FileTo> arrayList = new ArrayList<>();
        arrayList.add(fileService.findFileToById(fileId));

        model.addAttribute("files",  arrayList);
        return "/files/listFiles1";
    }

    //Работа с аудио
    @ApiOperation("Показывает все аудио")
    @GetMapping("/audios")
    public String audioList(Model model) {
        model.addAttribute("audios", audioService.getAll());
        return "/audios/listAudios";
    }

    @ApiOperation("Удаляет аудио")
    @PostMapping("/deleteAudio")
    public String deleteAudio(@RequestParam(defaultValue = "") Long audioId,
                              @RequestParam(defaultValue = "") String action) {
        if (action.equals("delete")) {
            audioService.deleteAudio(audioId);
        }
        return "redirect:/admin/audios";
    }

    @ApiOperation("Аудио по id")
    @GetMapping("/audio/{audioId}")
    public String getAudio(@PathVariable Long audioId, Model model) {
        model.addAttribute("audio", audioService.findAudioToById(audioId));
        model.addAttribute("tagList", tagService.getAll());
        return "/audios/audio";
    }

    @ApiOperation("Добавление тэга к аудио")
    @GetMapping("/audios/{audioId}/{tagId}")
    public String addTagToAudio(@PathVariable long audioId, @PathVariable long tagId, Model model) {
        audioService.addTagToAudio(audioId, tagId);
        return getAudio(audioId, model);
    }


    //Работа с плейлистами
    @ApiOperation("Показывает лист плейлистов")
    @GetMapping("/playLists")
    public String playListList(Model model) {
        model.addAttribute("playLists", playListService.getAll());
        return "/playLists/listPlayList";
    }

    @ApiOperation("Удаляет плейлист")
    @PostMapping("/deletePlayList")
    public String deletePlayList(@RequestParam(defaultValue = "") Long playListId) {
        playListService.deletePlayList(playListId);
        return "redirect:/admin/playLists";
    }

    //Работа с тэгами
    @ApiOperation("Показывает лист тэгов")
    @GetMapping("/tags")
    public String tagList(Model model) {
        model.addAttribute("tags", tagService.getAll());
        return "/tags/listTags";
    }

    @ApiOperation("Удаляет тэг")
    @PostMapping("/deleteTag")
    public String deleteTag(@RequestParam(defaultValue = "") Long tagId) {
        tagService.deleteTag(tagId);
        return "redirect:/admin/tags";
    }

    @PostMapping("/addTag")
    public String addTag(@RequestParam String name) {
        tagService.addTag(name);
        return "redirect:/admin/tags";
    }

    @ApiOperation("Показывает тэг по id")
    @GetMapping("/getTag/{tagId}")
    public String getTag(@PathVariable("tagId") Long tagId, Model model) {
        model.addAttribute("tags", tagService.findTagToById(tagId));
        return "/tags/listTags";
    }
}

package com.controller;

import com.model.PlayList;
import com.model.User;
import com.service.AudioService;
import com.service.PlayListService;
import com.service.UserService;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Api
@Controller
public class UserContentController {

    private UserService userService;

    private PlayListService playListService;

    private AudioService audioService;

    public UserContentController(UserService userService, PlayListService playListService, AudioService audioService) {
        this.userService = userService;
        this.playListService = playListService;
        this.audioService = audioService;
    }

    @GetMapping("/user")
    public String getUserPage(Authentication authentication, Model model) {
        try {
            User user = userService.findUserById(((User) authentication.getPrincipal()).getId());
            model.addAttribute("user", user);
            return "users/userPage";
        } catch (NotFoundException e) {
            return "exception";
        }
    }

    @PostMapping("/user")
    public String createNewPlayList(@RequestParam String name,
                                    @RequestParam(defaultValue = "false") boolean isPrivate,
                                    Authentication authentication, Model model) {
        try {
            User user = (User) authentication.getPrincipal();
            playListService.addPlayList(user.getId(), name, isPrivate);
            return getUserPage(authentication, model);
        } catch (NotFoundException e) {
            return "exception";
        }

    }

    @GetMapping("/user/playList/{id}")
    public String getUserPlayList(@PathVariable Long id, Authentication authentication, Model model) {
        try {
            PlayList nowPlayList = playListService.foundPlayListById(id);
            if (nowPlayList.isPrivate() && !(((User) authentication.getPrincipal()).getId().equals(nowPlayList.getOwner().getId()))) {
                return "exception";
            }
            model.addAttribute("playList", nowPlayList);
        } catch (NotFoundException e) {
            return "exception";
        }
        return "playLists/playListPage";
    }


    @PostMapping("/user/playList/{id}")
    public String deleteAudio(@PathVariable Long id,
                              @RequestParam(required = true, defaultValue = "") Long audioId,
                              @RequestParam(required = true, defaultValue = "") String action,
                              Authentication authentication, Model model) {
        if (action.equals("delete")) {
            try {
                playListService.deleteAudioFromPlayList(id, audioId);
            } catch (NotFoundException e) {
                return "exception";
            }
        }

        return getUserPlayList(id, authentication, model);
    }

    @PostMapping("/user/playList/{id}/add")
    public String addAudioToPlayList(@PathVariable Long id,
                                     @RequestParam(required = true, defaultValue = "") String name,
                                     Authentication authentication, Model model) {
        try {
            playListService.addAudio(id, audioService.foundAudioByName(name).getId());
        } catch (NotFoundException e) {
            return "exception";
        }

        return getUserPlayList(id, authentication, model);
    }

}

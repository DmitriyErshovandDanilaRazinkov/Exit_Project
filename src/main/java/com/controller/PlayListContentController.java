package com.controller;

import com.model.PlayList;
import com.model.User;
import com.service.AudioService;
import com.service.PlayListService;
import com.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class PlayListContentController {

    private UserService userService;

    private PlayListService playListService;

    private AudioService audioService;


    public PlayListContentController(UserService userService, PlayListService playListService, AudioService audioService) {
        this.userService = userService;
        this.playListService = playListService;
        this.audioService = audioService;
    }

    @GetMapping("/playLists/{id}")
    public String getUserPlayList(@PathVariable Long id, Authentication authentication, Model model) {

        PlayList nowPlayList = playListService.findPlayListById(id);
        if (nowPlayList.isPrivate() &&
                !(playListService.checkUser(nowPlayList, ((User) authentication.getPrincipal()).getId()))) {
            model.addAttribute("exceptionText", "У Вас не доступа к этому плейлисту");
            return "exception";
        }
        model.addAttribute("playList", nowPlayList);

        return "playLists/playListPage";
    }


    @PostMapping("playLists/{id}")
    public String deleteAudio(@PathVariable Long id,
                              @RequestParam(defaultValue = "") Long audioId,
                              @RequestParam(defaultValue = "") String action,
                              Authentication authentication, Model model) {
        if (action.equals("delete")) {
            playListService.deleteAudioFromPlayList(id, audioId);
        }

        return getUserPlayList(id, authentication, model);
    }

    @GetMapping("playLists/{id}/users")
    public String getUsers(@PathVariable Long id, Authentication authentication, Model model) {

        Set<User> userList = playListService.findPlayListById(id).getUsers();
        User nowUser = userService.findUserById(((User) authentication.getPrincipal()).getId());

        if (!userList.contains(userService.findUserById(nowUser.getId()))) {
            model.addAttribute("exceptionText", "У Вас не доступа к этому плейлисту");
            return "exception";
        }

        model.addAttribute("playListId", id);
        model.addAttribute("listUser", userList);

        model.addAttribute("nowUserRole", nowUser.getRoleInPlayLists().get(id).getPlayListRole());

        return "playLists/userList";
    }

    @PostMapping("playLists/{id}/addUser")
    public String addUserInPlayList(@PathVariable Long id, @RequestParam("userId") Long userId, Authentication authentication, Model model) {

        playListService.addNewUserToPlayList(id, userId);

        return getUsers(id, authentication, model);
    }

    @PostMapping("playLists/{id}/deleteUser")
    public String deleteUserFromList(@PathVariable Long id, @RequestParam("userId") Long userId, Authentication authentication, Model model) {

        playListService.deleteUser(id, userId);

        return getUsers(id, authentication, model);
    }
}
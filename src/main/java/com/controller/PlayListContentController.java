package com.controller;

import com.model.PlayList;
import com.model.Role_PlayList;
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
        User nowUser = userService.findUserById(((User) authentication.getPrincipal()).getId());

        if (nowPlayList.isPrivate() &&
                !(playListService.checkUser(nowPlayList, nowUser.getId()))) {
            model.addAttribute("exceptionText", "У Вас не доступа к этому плейлисту");
            return "exception";
        }

        model.addAttribute("playList", nowPlayList);

        model.addAttribute("userRole", nowUser.getRoleInPlayLists().get(id).getPlayListRole());


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

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        return getUserPlayList(id, authentication, model);
    }


    @GetMapping("playLists/{id}/users")
    public String getUsers(@PathVariable Long id, Authentication authentication, Model model) {

        PlayList nowPlayList = playListService.findPlayListById(id);
        User nowUser = userService.findUserById(((User) authentication.getPrincipal()).getId());

        if (!userService.checkUserRights(nowUser.getId(), id, Role_PlayList.ROLE_USER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этому плейлисту");
            return "exception";
        }

        model.addAttribute("playListId", id);
        model.addAttribute("listUserWithRole", nowPlayList.getRoleInPlayLists());
        model.addAttribute("joinLink", "http://localhost:8080/playLists/join/" + nowPlayList.getJoinCode());

        model.addAttribute("nowUserRole", nowUser.getRoleInPlayLists().get(id).getPlayListRole());

        return "playLists/userList";
    }

    @PostMapping("playLists/{id}/addUser")
    public String addUserInPlayList(@PathVariable Long id, @RequestParam("userId") Long userId, Authentication authentication, Model model) {

        User nowUser = userService.findUserById(((User) authentication.getPrincipal()).getId());

        if (!userService.checkUserRights(nowUser.getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.addNewUserToPlayList(id, userId);

        return getUsers(id, authentication, model);
    }

    @PostMapping("playLists/{id}/deleteUser")
    public String deleteUserFromList(@PathVariable Long id, @RequestParam("userId") Long userId, Authentication authentication, Model model) {

        User nowUser = userService.findUserById(((User) authentication.getPrincipal()).getId());

        if (!userService.checkUserRights(nowUser.getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        if (userService.findUserById(userId).getRoleInPlayLists().get(id).getPlayListRole().compare(Role_PlayList.ROLE_ADMIN) &&
                nowUser.getRoleInPlayLists().get(id).getPlayListRole().compare(Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "Админ не может удалить админа");
            return "exception";
        }

        playListService.deleteUser(id, userId);

        return getUsers(id, authentication, model);
    }

    @GetMapping("playLists/{id}/admins")
    public String getAdminPage(@PathVariable Long id, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        model.addAttribute("playListId", id);
        model.addAttribute("listUserWithRole", playListService.getListWithAndUnderRole(id, Role_PlayList.ROLE_ADMIN));

        return "playLists/adminList";
    }

    @PostMapping("playLists/{id}/addAdmin")
    public String addAdmin(@PathVariable Long id, @RequestParam Long userId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_ADMIN);

        return getAdminPage(id, authentication, model);
    }

    @PostMapping("playLists/{id}/deleteAdmin")
    public String deleteAdmin(@PathVariable Long id, @RequestParam Long userId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_USER);

        return getAdminPage(id, authentication, model);
    }

    @GetMapping("playLists/{id}/moderators")
    public String getModeratorPage(@PathVariable Long id, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        model.addAttribute("playListId", id);
        model.addAttribute("listUserWithRole", playListService.getListWithAndUnderRole(id, Role_PlayList.ROLE_MODERATOR));

        return "playLists/moderatorList";
    }

    @PostMapping("playLists/{id}/addModerator")
    public String addModerator(@PathVariable Long id, @RequestParam Long userId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_MODERATOR);

        return getModeratorPage(id, authentication, model);
    }

    @PostMapping("playLists/{id}/deleteModerator")
    public String deleteModerator(@PathVariable Long id, @RequestParam Long userId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_USER);

        return getModeratorPage(id, authentication, model);
    }

    @GetMapping("playLists/{id}/changeOwner")
    public String getChangeOwnerPage(@PathVariable Long id, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        model.addAttribute("playListId", id);
        model.addAttribute("listUserWithRole", playListService.findPlayListById(id).getRoleInPlayLists());

        return getAdminPage(id, authentication, model);
    }

    @GetMapping("playLists/join/{joinCode}")
    public String joinInPlayList(@PathVariable("joinCode") String joinCode, Authentication authentication, Model model) {

        return getUserPlayList(playListService.joinUser(joinCode, ((User) authentication.getPrincipal()).getId()),
                authentication, model);
    }
}
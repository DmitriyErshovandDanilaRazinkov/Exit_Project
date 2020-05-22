package com.controller;

import com.model.DTO.pages.playList.PlayListListUserTO;
import com.model.DTO.pages.playList.PlayListUsersTO;
import com.model.PlayList;
import com.model.Role_PlayList;
import com.model.User;
import com.service.PlayListService;
import com.service.RoleInPlayListsService;
import com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class PlayListContentController {

    private UserService userService;

    private PlayListService playListService;

    private RoleInPlayListsService roleInPlayListsService;

    @GetMapping("/playLists/{id}")
    public String getUserPlayList(@PathVariable Long id, Authentication authentication, Model model) {

        PlayList nowPlayList = playListService.findPlayListById(id);
        User nowUser = userService.findUserById(((User) authentication.getPrincipal()).getId());

        if (nowPlayList.isPrivate() &&
                !userService.checkUserRights(nowUser.getId(), nowPlayList.getId(), Role_PlayList.ROLE_NONE)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этому плейлисту");
            return "exception";
        }

        model.addAttribute("playList", nowPlayList);

        model.addAttribute("userRole", roleInPlayListsService.getUserRoleInPlayList(nowUser.getId(), nowPlayList.getId()));


        return "playLists/playListPage";
    }


    @PostMapping("/playLists/{id}")
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


    @GetMapping("/playLists/{id}/listUsers")
    public String getUsers(@PathVariable Long id, Authentication authentication, Model model) {

        PlayList nowPlayList = playListService.findPlayListById(id);
        User nowUser = userService.findUserById(((User) authentication.getPrincipal()).getId());

        if (!userService.checkUserRights(nowUser.getId(), id, Role_PlayList.ROLE_USER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этому плейлисту");
            return "exception";
        }

        PlayListListUserTO pageTo = new PlayListListUserTO();

        pageTo.setPlayListId(id);
        pageTo.setRoleInPlayListList(nowPlayList.getRoleInPlayLists());
        pageTo.setJoinCode("http://localhost:8080/playLists/join/" + nowPlayList.getJoinCode());
        pageTo.setNowUserRole(roleInPlayListsService.getUserRoleInPlayList(nowUser.getId(), nowPlayList.getId())
                .getPlayListRole().getId());

        model.addAttribute("pageTo", pageTo);

        return "playLists/userList";
    }


    @PostMapping("/playLists/{id}/deleteUser")
    public String deleteUserFromList(@PathVariable Long id, @RequestParam("userId") Long userId, Authentication authentication, Model model) {

        User nowUser = userService.findUserById(((User) authentication.getPrincipal()).getId());

        if (!userService.checkUserRights(nowUser.getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        if (roleInPlayListsService.getUserRoleInPlayList(userId, id).getPlayListRole().compare(Role_PlayList.ROLE_ADMIN) &&
                roleInPlayListsService.getUserRoleInPlayList(nowUser.getId(), id).getPlayListRole().compare(Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "Админ не может удалить админа");
            return "exception";
        }

        playListService.deleteUser(id, userId);

        return "redirect:/playLists/{id}/listUsers";
    }

    @GetMapping("/playLists/{id}/admins")
    public String getAdminPage(@PathVariable Long id, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        PlayListUsersTO pageTo = new PlayListUsersTO();

        pageTo.setPlayListId(id);
        pageTo.setRoleInPlayListList(playListService.getListWithAndUnderRole(id, Role_PlayList.ROLE_ADMIN));

        model.addAttribute("pageTo", pageTo);

        return "playLists/adminList";
    }

    @PostMapping("playLists/{id}/addAdmin")
    public String addAdmin(@PathVariable Long id, @RequestParam Long userId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_ADMIN);

        return "redirect:/playLists/{id}/admins";
    }

    @PostMapping("playLists/{id}/deleteAdmin")
    public String deleteAdmin(@PathVariable Long id, @RequestParam Long userId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_USER);

        return "redirect:/playLists/{id}/admins";
    }

    @GetMapping("playLists/{id}/moderators")
    public String getModeratorPage(@PathVariable Long id, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }
        PlayListUsersTO pageTo = new PlayListUsersTO();

        pageTo.setPlayListId(id);
        pageTo.setRoleInPlayListList(playListService.getListWithAndUnderRole(id, Role_PlayList.ROLE_MODERATOR));

        model.addAttribute("pageTo", pageTo);

        return "playLists/moderatorList";
    }

    @PostMapping("playLists/{id}/addModerator")
    public String addModerator(@PathVariable Long id, @RequestParam Long userId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_MODERATOR);

        return "redirect:playLists/{id}/addModerator";
    }

    @PostMapping("playLists/{id}/deleteModerator")
    public String deleteModerator(@PathVariable Long id, @RequestParam Long userId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_ADMIN)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_USER);

        return "redirect:playLists/{id}/addModerator";
    }

    @GetMapping("playLists/{id}/changeOwner")
    public String getChangeOwnerPage(@PathVariable Long id, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        PlayListUsersTO pageTo = new PlayListUsersTO();

        pageTo.setPlayListId(id);
        pageTo.setRoleInPlayListList(playListService.getListWithAndUnderRole(id, Role_PlayList.ROLE_MODERATOR));

        model.addAttribute("pageTo", pageTo);

        return "redirect:/playLists/{id}/admins";
    }

    @GetMapping("playLists/join/{joinCode}")
    public String joinInPlayList(@PathVariable("joinCode") String joinCode, Authentication authentication, Model model) {

        return getUserPlayList(playListService.joinUser(joinCode, ((User) authentication.getPrincipal()).getId()),
                authentication, model);
    }
}
package com.controller;

import com.exceptions.DontHaveRightsException;
import com.model.DTO.PlayListTo;
import com.model.DTO.pages.playList.PlayListListUserTO;
import com.model.DTO.pages.playList.PlayListPageTo;
import com.model.DTO.pages.playList.PlayListUsersTO;
import com.model.User;
import com.model.enums.Role_PlayList;
import com.service.PlayListService;
import com.service.RoleInPlayListsService;
import com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.RolesAllowed;

@AllArgsConstructor
@Controller
@RolesAllowed({User.ROLE_USER, User.ROLE_ADMIN})
public class PlayListContentController {

    private UserService userService;

    private PlayListService playListService;

    private RoleInPlayListsService roleInPlayListsService;

    @GetMapping("/playLists/{id}")
    public String getUserPlayList(@PathVariable Long id, Model model) {

        PlayListTo nowPlayList = playListService.findPlayListToById(id);

        if (nowPlayList.isPrivate() &&
                !userService.checkUserRights(nowPlayList.getId(), Role_PlayList.ROLE_NONE)) {
            throw new DontHaveRightsException("У Вас не доступа к этому плейлисту");
        }

        PlayListPageTo pageTo = new PlayListPageTo();
        pageTo.setPlayList(nowPlayList);
        pageTo.setUserRole(roleInPlayListsService.getToUserRoleInPlayList(UserService.getCurrentUser().getId(), nowPlayList.getId()));
        pageTo.setAudioList(playListService.getPlayListAudios(id));

        model.addAttribute("pageTo", pageTo);

        return "playLists/playListPage";
    }


    @PostMapping("/playLists/{id}/deleteAudio")
    public String deleteAudio(@PathVariable Long id,
                              @RequestParam(defaultValue = "") Long audioId,
                              @RequestParam(defaultValue = "") String action) {

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_MODERATOR)) {
            throw new DontHaveRightsException("У Вас не доступа к этой операции");
        }

        if (action.equals("delete")) {
            playListService.deleteAudioFromPlayList(id, audioId);
        }

        return "redirect:/playLists/{id}";
    }


    @GetMapping("/playLists/{id}/listUsers")
    public String getUsers(@PathVariable Long id, Model model) {

        PlayListTo nowPlayList = playListService.findPlayListToById(id);

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_USER)) {
            throw new DontHaveRightsException("У Вас не доступа кэтому плейлисту");
        }

        PlayListListUserTO pageTo = new PlayListListUserTO();

        pageTo.setPlayListId(id);
        pageTo.setRoleInPlayListList(playListService.getRoleInPlayLists(id));
        pageTo.setJoinCode("http://localhost:8080/playLists/join/" + nowPlayList.getJoinCode());
        pageTo.setNowUserRole(roleInPlayListsService.getToUserRoleInPlayList(UserService.getCurrentUser().getId(), nowPlayList.getId())
                .getPlayListRole().getId());

        model.addAttribute("pageTo", pageTo);

        return "playLists/userList";
    }


    @PostMapping("/playLists/{id}/deleteUser")
    public String deleteUserFromList(@PathVariable Long id, @RequestParam("userId") Long userId) {


        if (!userService.checkUserRights(id, Role_PlayList.ROLE_ADMIN)) {
            throw new DontHaveRightsException("У Вас не доступа к этой операции");
        }

        if (roleInPlayListsService.getToUserRoleInPlayList(userId, id).getPlayListRole().compare(Role_PlayList.ROLE_ADMIN) &&
                roleInPlayListsService.getToUserRoleInPlayList(UserService.getCurrentUser().getId(), id)
                        .getPlayListRole().compare(Role_PlayList.ROLE_OWNER)) {
            throw new DontHaveRightsException("Админ не может удалить админа");
        }

        playListService.deleteUser(id, userId);

        return "redirect:/playLists/{id}/listUsers";
    }

    @GetMapping("/playLists/{id}/admins")
    public String getAdminPage(@PathVariable Long id, Model model) {

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_OWNER)) {
            throw new DontHaveRightsException("У Вас не доступа к этой операции");
        }

        PlayListUsersTO pageTo = new PlayListUsersTO();

        pageTo.setPlayListId(id);
        pageTo.setRoleInPlayListList(playListService.getListWithAndUnderRole(id, Role_PlayList.ROLE_ADMIN));

        model.addAttribute("pageTo", pageTo);

        return "playLists/adminList";
    }

    @PostMapping("/playLists/{id}/addAdmin")
    public String addAdmin(@PathVariable Long id, @RequestParam Long userId) {

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_OWNER)) {
            throw new DontHaveRightsException("У Вас не доступа к этой операции");
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_ADMIN);

        return "redirect:/playLists/{id}/admins";
    }

    @PostMapping("/playLists/{id}/deleteAdmin")
    public String deleteAdmin(@PathVariable Long id, @RequestParam Long userId) {

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_OWNER)) {
            throw new DontHaveRightsException("У Вас не доступа к этой операции");
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_USER);

        return "redirect:/playLists/{id}/admins";
    }

    @GetMapping("/playLists/{id}/moderators")
    public String getModeratorPage(@PathVariable Long id, Model model) {

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_ADMIN)) {
            throw new DontHaveRightsException("У Вас не доступа к этой операции");
        }
        PlayListUsersTO pageTo = new PlayListUsersTO();

        pageTo.setPlayListId(id);
        pageTo.setRoleInPlayListList(playListService.getListWithAndUnderRole(id, Role_PlayList.ROLE_MODERATOR));

        model.addAttribute("pageTo", pageTo);

        return "playLists/moderatorList";
    }

    @PostMapping("/playLists/{id}/addModerator")
    public String addModerator(@PathVariable Long id, @RequestParam Long userId) {

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_ADMIN)) {
            throw new DontHaveRightsException("У Вас не доступа к этой операции");
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_MODERATOR);

        return "redirect:playLists/{id}/addModerator";
    }

    @PostMapping("/playLists/{id}/deleteModerator")
    public String deleteModerator(@PathVariable Long id, @RequestParam Long userId) {

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_ADMIN)) {
            throw new DontHaveRightsException("У Вас не доступа к этой операции");
        }

        playListService.changeUserRoleTo(id, userId, Role_PlayList.ROLE_USER);

        return "redirect:playLists/{id}/addModerator";
    }

    @GetMapping("/playLists/{id}/changeOwner")
    public String getChangeOwnerPage(@PathVariable Long id, Model model) {

        if (!userService.checkUserRights(id, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "У Вас не доступа к этой операции");
            return "exception";
        }

        PlayListUsersTO pageTo = new PlayListUsersTO();

        pageTo.setPlayListId(id);
        pageTo.setRoleInPlayListList(playListService.getListWithAndUnderRole(id, Role_PlayList.ROLE_MODERATOR));

        model.addAttribute("pageTo", pageTo);

        return "redirect:/playLists/{id}/admins";
    }

    @GetMapping("/playLists/join/{joinCode}")
    public String joinInPlayList(@PathVariable("joinCode") String joinCode, Model model) {
        playListService.joinUser(joinCode, UserService.getCurrentUser().getId());
        return "redirect:/user" ;
    }
}
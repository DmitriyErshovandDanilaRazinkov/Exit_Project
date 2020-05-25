package com.controller;

import com.model.Audio;
import com.model.DTO.AudioTo;
import com.model.DTO.pages.user.UserPageTo;
import com.model.DTO.pages.user.UserPageToAddPlaylist;
import com.model.Role_PlayList;
import com.model.User;
import com.service.AudioService;
import com.service.PlayListService;
import com.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


@AllArgsConstructor
@Api
@Controller
@RolesAllowed({"USER", "ADMIN"})
public class UserContentController {

    private UserService userService;

    private PlayListService playListService;

    private AudioService audioService;

    @GetMapping("/user")
    public String getUserPage(Model model) {
        UserPageTo pageTo = new UserPageTo();

        pageTo.setUser(UserService.getCurrentUser());
        pageTo.setPlayLists(playListService.getPlayListsByUser(pageTo.getUser().getId()));
        model.addAttribute("pageTo", pageTo);

        return "users/userPage";
    }

    @PostMapping("/addPlayList")
    public String createNewPlayList(@RequestParam String name,
                                    @RequestParam(defaultValue = "false") boolean isPrivate,
                                    Model model) {
        playListService.addPlayList(UserService.getCurrentUser().getId(), name, isPrivate);
        return "redirect:/user";
    }

    @GetMapping("/audio")
    public String getListAudio(Model model) {

        model.addAttribute("listAudio", audioService.getAll());

        return "/audios/userListAudios";
    }

    @GetMapping("/audio/addInPlayList/{id}")
    public String addInPlayList(@PathVariable("id") Long id, Model model) {

        UserPageToAddPlaylist userPageToAddPlaylist = new UserPageToAddPlaylist();
        userPageToAddPlaylist.setPlayLists(playListService.getPlayListsByUser(UserService.getCurrentUser().getId()));
        userPageToAddPlaylist.setResult("");

        //model.addAttribute("playLists", playListService.getPlayListsByUser(UserService.getCurrentUser().getId()));
        //model.addAttribute("result", "");

        model.addAttribute("userPageTo", userPageToAddPlaylist);

        return "/playLists/addAudioInPlayList";
    }

    @PostMapping("/audio/addInPlayList/{id}")
    public String addInPlayList(@PathVariable("id") Long id, @RequestParam("playListId") Long playListId, Model model) {

        if (!userService.checkUserRights(playListId, Role_PlayList.ROLE_MODERATOR)) {
            model.addAttribute("exceptionText", "Вы не имеете прав");
            return "exception";
        }

        playListService.addAudio(playListId, id);

        UserPageToAddPlaylist userPageToAddPlaylist = new UserPageToAddPlaylist();

        userPageToAddPlaylist.setPlayLists(playListService.getPlayListsByUser(UserService.getCurrentUser().getId()));
        userPageToAddPlaylist.setResult("Аудио добавлено");

        //model.addAttribute("playLists", playListService.getPlayListsByUser(UserService.getCurrentUser().getId()));
        //model.addAttribute("result", "Аудио добавлено");

        model.addAttribute("userPageTo",userPageToAddPlaylist);

        return "/playLists/addAudioInPlayList";
    }

    @GetMapping("/user/audioPage/{audioId}")
    public String getAudioPage(@PathVariable("audioId") Long audioId, Model model) {

        AudioTo nowAudio = audioService.findAudioToById(audioId);
        if (nowAudio.isPremium() && !(userService.checkUserPremium())) {
            model.addAttribute("exceptionText", "У Вас нет премиума");
            return "/exception";
        }

        model.addAttribute("audio", nowAudio);

        return "/audios/audioPage";
    }

    @GetMapping("/store/premium")
    public String getBuyPremiumPage(Model model) {
        model.addAttribute("message", "");
        model.addAttribute("exception", "");
        return "store/premiumStore";
    }

    @PostMapping("/store/premium")
    public String buyPremium(Model model) {


        if (userService.buyPremium(UserService.getCurrentUser().getId(), 3600L * 24L * 10L, 30)) {
            model.addAttribute("message", "Премиум куплен");
            model.addAttribute("exception", "");
        } else {
            model.addAttribute("message", "");
            model.addAttribute("exception", "Не хватает средств");
        }

        return "store/premiumStore";
    }

    @PostMapping("/store/addCash")
    public String addCash(Model model) {

        model.addAttribute("message", "Счет пополнен");
        model.addAttribute("exception", "");
        userService.addCash(UserService.getCurrentUser().getId(), 50D);

        return "store/premiumStore";
    }

    @PostMapping("playList/delete")
    public String deletePlayList(@RequestParam Long playListId, Model model) {

        if (!userService.checkUserRights(playListId, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "Вы не имеете прав");
            return "exception";
        }

        playListService.deletePlayList(playListId);

        return "redirect:/user";
    }
}

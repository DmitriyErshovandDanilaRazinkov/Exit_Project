package com.controller;

import com.model.Audio;
import com.model.DTO.user.UserPageTo;
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


@AllArgsConstructor
@Api
@Controller
public class UserContentController {

    private UserService userService;

    private PlayListService playListService;

    private AudioService audioService;

    @GetMapping("/user")
    public String getUserPage(Authentication authentication, Model model) {
        UserPageTo pageTo = new UserPageTo();

        pageTo.setUser(userService.findUserById(((User) authentication.getPrincipal()).getId()));
        pageTo.setPlayLists(playListService.getPlayListsByUser(pageTo.getUser().getId()));
        model.addAttribute("pageTo", pageTo);

        return "users/userPage";
    }

    @PostMapping("/addPlayList")
    public String createNewPlayList(@RequestParam String name,
                                    @RequestParam(defaultValue = "false") boolean isPrivate,
                                    Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        playListService.addPlayList(user.getId(), name, isPrivate);
        return "redirect:/user";
    }

    @GetMapping("/audio")
    public String getListAudio(Model model) {

        model.addAttribute("listAudio", audioService.getAll());

        return "/audios/userListAudios";
    }

    @GetMapping("/audio/addInPlayList/{id}")
    public String addInPlayList(@PathVariable("id") Long id, Authentication authentication, Model model) {

        model.addAttribute("playLists", playListService.getPlayListsByUser(((User) authentication.getPrincipal()).getId()));

        model.addAttribute("result", "");

        return "/playLists/addAudioInPlayList";
    }

    @PostMapping("/audio/addInPlayList/{id}")
    public String addInPlayList(@PathVariable("id") Long id, @RequestParam("playListId") Long playListId,
                                Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), playListId, Role_PlayList.ROLE_MODERATOR)) {
            model.addAttribute("exceptionText", "Вы не имеете прав");
            return "exception";
        }

        playListService.addAudio(playListId, id);

        model.addAttribute("playLists", playListService.getPlayListsByUser(((User) authentication.getPrincipal()).getId()));

        model.addAttribute("result", "Аудио добавлено");

        return "/playLists/addAudioInPlayList";
    }

    @GetMapping("/user/audioPage/{audioId}")
    public String getAudioPage(@PathVariable("audioId") Long audioId, Authentication authentication, Model model) {

        Audio nowAudio = audioService.foundAudioById(audioId);
        if (nowAudio.isPremium() && !(userService.checkUserPremium(((User) authentication.getPrincipal()).getId()))) {
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
    public String buyPremium(Authentication authentication, Model model) {

        if (userService.buyPremium(((User) authentication.getPrincipal()).getId(), 3600L * 24L * 10L, 30)) {
            model.addAttribute("message", "Премиум куплен");
            model.addAttribute("exception", "");
        } else {
            model.addAttribute("message", "");
            model.addAttribute("exception", "Не хватает средств");
        }

        return "store/premiumStore";
    }

    @PostMapping("/store/addCash")
    public String addCash(Authentication authentication, Model model) {

        model.addAttribute("message", "Счет пополнен");
        model.addAttribute("exception", "");
        userService.addCash(((User) authentication.getPrincipal()).getId(), 50D);

        return "store/premiumStore";
    }

    @PostMapping("playList/delete")
    public String deletePlayList(@RequestParam Long playListId, Authentication authentication, Model model) {

        if (!userService.checkUserRights(((User) authentication.getPrincipal()).getId(), playListId, Role_PlayList.ROLE_OWNER)) {
            model.addAttribute("exceptionText", "Вы не имеете прав");
            return "exception";
        }

        playListService.deletePlayList(playListId);

        return "redirect:/user";
    }
}

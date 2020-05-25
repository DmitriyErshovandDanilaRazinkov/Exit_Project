package com.controller;

import com.model.DTO.UserFormTO;
import com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@AllArgsConstructor
@Controller
@RequestMapping("/registration")
public class RegistrationController extends BaseController {

    private UserService userService;

    @GetMapping
    public String registration(Model model) {
        return "login/registration";
    }

    @PostMapping
    public String addUser(@Valid UserFormTO userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                          Model model) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userForm", userForm);
            addValidationMessage(redirectAttributes, bindingResult);
            return "redirect:/registration";
        }
        if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userForm", userForm);
            redirectAttributes.addFlashAttribute("passwordsNotEquals", "Пароли не совпадают");
            return "redirect:/registration";
        }

        userService.addUser(userForm);

        return "redirect:/";
    }
}

package com.controller;

import com.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class UserController {

    UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public String getAllUsers(Map<String, Object> model){

        model.put("users", repository.findAll());

        return "users/listUsers";
    }


}

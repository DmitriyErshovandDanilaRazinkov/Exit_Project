package com.controller;

import com.model.User;
import com.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api
@Controller
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @ApiOperation("Получение списка пользователей")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.allUsers());
    }

    @ApiOperation("Получение пользователя по id")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return ResponseEntity.ok(service.findUserById(id));
    }

    @ApiOperation("Удаление пользователя")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}

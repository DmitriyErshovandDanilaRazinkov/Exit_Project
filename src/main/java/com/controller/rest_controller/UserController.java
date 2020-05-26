package com.controller.rest_controller;

import com.model.DTO.UserDetailsTo;
import com.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Api
@RestController
public class UserController {

    private UserService service;

    @ApiOperation("Получение списка пользователей")
    @GetMapping("/users")
    public ResponseEntity<List<UserDetailsTo>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(service.allUsers());
    }

    @ApiOperation("Получение пользователя по id")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findUserToById(id));
    }

    @ApiOperation("Удаление пользователя")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

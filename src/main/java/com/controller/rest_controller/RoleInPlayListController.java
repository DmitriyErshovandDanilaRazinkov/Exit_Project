package com.controller.rest_controller;

import com.model.DTO.RoleInPlayListTo;
import com.model.composite_key.RoleInPlayListId;
import com.service.RoleInPlayListsService;
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
public class RoleInPlayListController {

    private RoleInPlayListsService service;

    @ApiOperation("Получение списка ролей")
    @GetMapping("/roles")
    public ResponseEntity<List<RoleInPlayListTo>> getAllAudios() {
        return (ResponseEntity.status(HttpStatus.OK).body(service.getAllTo()));
    }

    @ApiOperation("Получение роли по id")
    @GetMapping("/roles/{id}")
    public ResponseEntity<?> getRole(@PathVariable RoleInPlayListId id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getToUserRoleInPlayList(id));
    }

    @ApiOperation("Удаление роли")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> deleteAudio(@PathVariable RoleInPlayListId id) {
        service.deleteRoleInPlayList(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

package com.controller;

import com.model.Tag;
import com.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Api
@Controller
public class TagController {

    private TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @ApiOperation("Получение списка тэгов")
    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(service.getAll());
    }

    @ApiOperation("Получение тэга по id")
    @GetMapping("/tags/{id}")
    public ResponseEntity<?> getFile(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.foundTagById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ApiOperation("Удаление файла")
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        service.deleteTag(id);
        return ResponseEntity.ok().build();
    }

}
package com.controller;

import com.model.DTO.TagTo;
import com.model.Tag;
import com.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@AllArgsConstructor
@Api
@Controller
public class TagController {

    private TagService service;

    @ApiOperation("Получение списка тэгов")
    @GetMapping("/tags")
    public ResponseEntity<List<TagTo>> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @ApiOperation("Получение тэга по id")
    @GetMapping("/tags/{id}")
    public ResponseEntity<?> getFile(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findTagToById(id));
    }

    @ApiOperation("Удаление файла")
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        service.deleteTag(id);
        return ResponseEntity.ok().build();
    }

}
package com.controller;

import com.model.DTO.PlayListTo;
import com.model.PlayList;
import com.service.PlayListService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class PlayListController {

    private PlayListService service;

    @ApiOperation("Получение списка плей листов")
    @GetMapping("/playLists")
    public ResponseEntity<List<PlayListTo>> getAllFiles() {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.getAll());
    }

    @ApiOperation("Получение плей листа по id")
    @GetMapping("/playList/{id}")
    public ResponseEntity<?> getAudio(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(service.findPlayListToById(id));
    }

    @ApiOperation("Удаление плей листа")
    @DeleteMapping("/playList/{id}")
    public ResponseEntity<?> deletePlayList(@PathVariable long id) {
        service.deletePlayList(id);
        return ResponseEntity.ok().build();
    }
}

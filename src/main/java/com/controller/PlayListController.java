package com.controller;

import com.model.PlayList;
import com.service.PlayListService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public class PlayListController {

    private PlayListService service;

    public PlayListController(PlayListService service) {
        this.service = service;
    }

    @ApiOperation("Получение списка плей листов")
    @GetMapping("/playLists")
    public ResponseEntity<List<PlayList>> getAllFiles() {
        return ResponseEntity.ok(service.getAll());
    }

    @ApiOperation("Получение плей листа по id")
    @GetMapping("/playLists/{id}")
    public ResponseEntity<?> getAudio(@PathVariable long id) {
        return ResponseEntity.ok(service.findPlayListById(id));
    }

    @ApiOperation("Удаление плей листа")
    @DeleteMapping("/playLists/{id}")
    public ResponseEntity<?> deletePlayList(@PathVariable long id) {
        service.deletePlayList(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/playLists/{playListId}/{audioId}")
    public ResponseEntity<?> addAudioToPlayList(@PathVariable long playListId, @PathVariable long audioId) {
        service.addAudio(playListId, audioId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("Удаление аудио из плей листа")
    @DeleteMapping("/playLists/{playListId}/{audioId}")
    public ResponseEntity<?> deleteAudioInPlayList(@PathVariable long playListId, @PathVariable long audioId) {
        service.deleteAudioFromPlayList(playListId, audioId);
        return ResponseEntity.ok().build();
    }
}

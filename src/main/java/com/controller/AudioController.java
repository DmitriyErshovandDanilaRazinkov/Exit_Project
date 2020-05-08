package com.controller;

import com.model.Audio;
import com.service.AudioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AudioController {

    private AudioService service;

    public AudioController(AudioService service) {
        this.service = service;
    }

    @ApiOperation("Получение списка аудио")
    @GetMapping("/audios")
    public ResponseEntity<List<Audio>> getAllAudios() {
        return ResponseEntity.ok(service.getAll());
    }

    @ApiOperation("Получение аудио по id")
    @GetMapping("/audios/{id}")
    public ResponseEntity<?> getAudio(@PathVariable long id) {
        return ResponseEntity.ok(service.foundAudioById(id));
    }

    @ApiOperation("Удаление аудио")
    @DeleteMapping("/audios/{id}")
    public ResponseEntity<?> deleteAudio(@PathVariable long id) {
        service.deleteAudio(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/audios/{audioId}/{tagId}")
    public ResponseEntity<?> addTagToAudio(@PathVariable long audioId, @PathVariable long tagId) {
        service.addTagToAudio(audioId, tagId);
        return ResponseEntity.ok().build();
    }
}


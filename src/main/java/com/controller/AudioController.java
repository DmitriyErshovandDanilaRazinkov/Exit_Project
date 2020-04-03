package com.controller;

import com.model.Audio;
import com.service.AudioService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<Audio>> getAllFiles() {
        return ResponseEntity.ok(service.getAll());
    }

    @ApiOperation("Получение аудио по id")
    @GetMapping("/audios/{id}")
    public ResponseEntity<?> getAudio(@PathVariable long id) {
        try {
            return ResponseEntity.ok(service.foundAudioById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @ApiOperation("Удаление аудио")
    @DeleteMapping("/audios/{id}")
    public ResponseEntity<?> deleteAudio(@PathVariable long id) {
        service.deleteAudio(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/audios/{audioId}/{tagId}")
    public ResponseEntity<?> addTagToAudio(@PathVariable long audioId, @PathVariable long tagId) {
        try {
            service.addTagToAudio(audioId, tagId);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}


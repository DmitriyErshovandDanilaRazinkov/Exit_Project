package com.controller.rest_controller;

import com.model.DTO.AudioTo;
import com.service.AudioService;
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
public class AudioController {

    private AudioService service;

    @ApiOperation("Получение списка аудио")
    @GetMapping("/audios")
    public ResponseEntity<List<AudioTo>> getAllAudios() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @ApiOperation("Получение аудио по id")
    @GetMapping("/audios/{id}")
    public ResponseEntity<?> getAudio(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAudioToById(id));
    }

    @ApiOperation("Удаление аудио")
    @DeleteMapping("/audios/{id}")
    public ResponseEntity<?> deleteAudio(@PathVariable long id) {
        service.deleteAudio(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/audios/{audioId}/{tagId}")
    public ResponseEntity<?> addTagToAudio(@PathVariable long audioId, @PathVariable long tagId) {
        service.addTagToAudio(audioId, tagId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}


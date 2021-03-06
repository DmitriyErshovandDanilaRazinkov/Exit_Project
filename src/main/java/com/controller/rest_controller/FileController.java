package com.controller.rest_controller;

import com.model.DTO.FileTo;
import com.model.FileAud;
import com.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Api
@RestController
public class FileController {

    private FileService service;

    @ApiOperation("Получение списка файлов")
    @GetMapping("/files")
    public ResponseEntity<List<FileTo>> getAllFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @ApiOperation("Получение файла по id")
    @GetMapping("/files/{id}")
    public ResponseEntity<?> getFile(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findFileToById(id));
    }

    @ApiOperation("Удаление файла")
    @DeleteMapping("/files/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        service.deleteFile(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

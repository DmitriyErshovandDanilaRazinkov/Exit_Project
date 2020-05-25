package com.controller;

import com.model.DTO.FileTo;
import com.model.FileAud;
import com.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@AllArgsConstructor
@Api
@Controller
public class FileController {

    private FileService service;

    @ApiOperation("Получение списка файлов")
    @GetMapping("/files")
    public ResponseEntity<List<FileTo>> getAllFiles() {
        return ResponseEntity.ok(service.getAll());
    }

    @ApiOperation("Получение файла по id")
    @GetMapping("/files/{id}")
    public ResponseEntity<?> getFile(@PathVariable long id) {
        return ResponseEntity.ok(service.findFileToById(id));
    }

    @ApiOperation("Удаление файла")
    @DeleteMapping("/files/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable long id) {
        service.deleteFile(id);
        return ResponseEntity.ok().build();
    }

}

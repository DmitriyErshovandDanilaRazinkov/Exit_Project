package com.service;

import com.model.DTO.FileTo;
import com.model.FileAud;
import com.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadPath;

    private FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public List<FileTo> getAll() {
        return repository.findAll().stream()
                .map(FileService::fileToFileTo)
                .collect(Collectors.toList());
    }

    public FileTo findFileToById(Long id) {
        return FileService.fileToFileTo(findFileById(id));
    }

    FileAud findFileById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Файл не найден"));
    }

    public void deleteFile(Long id) {
        File file = new File(uploadPath + '/' + repository.findById(id).orElseThrow(() ->
                new NotFoundException("Файл не найден")).getName());

        file.delete();

        repository.deleteById(id);
    }

    public void createNewFile(MultipartFile file) throws IOException {
        uploadFile(file);
    }

    FileAud uploadFile(MultipartFile file) throws IOException {
        if (file != null) {

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    throw new IOException("");
                }
            }

            String uuidFile = randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            FileAud newFile = new FileAud(resultFileName, file.getOriginalFilename());
            repository.save(newFile);
            return newFile;
        } else {
            throw new NullPointerException("Файл пустой");
        }
    }

    void saveFile(FileAud file) {
        repository.save(file);
    }

    public static FileTo fileToFileTo(FileAud file) {
        FileTo fileTo = new FileTo();
        fileTo.setId(file.getId());
        fileTo.setName(file.getName());
        fileTo.setOriginalName(file.getOriginalName());
        return fileTo;
    }
}

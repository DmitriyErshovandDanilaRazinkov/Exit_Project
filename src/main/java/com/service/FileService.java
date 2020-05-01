package com.service;

import com.model.FileAud;
import com.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.UUID.randomUUID;

@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private FileRepository repository;

    public List<FileAud> getAll() {
        return repository.findAll();
    }

    public FileAud foundFileById(long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        }
        return null;
    }

    public boolean deleteFile(Long id) {
        if (repository.findById(id).isPresent()) {
            File file = new File(uploadPath + '/' + repository.findById(id).get().getName());
            repository.deleteById(id);

            if (!file.delete()) {
                return false;
            }
        }
        return false;
    }

    public Long uploadFile(MultipartFile file) throws IOException {
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
            return newFile.getId();
        } else {
            return (long) -1;
        }
    }
}

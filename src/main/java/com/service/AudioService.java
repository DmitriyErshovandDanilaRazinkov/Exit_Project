package com.service;

import com.exceptions.NotFoundDataBaseException;
import com.model.Audio;
import com.model.FileAud;
import com.model.Tag;
import com.repository.AudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Service
public class AudioService {

    private AudioRepository repository;

    private FileService fileService;

    private TagService tagService;

    public void saveAudio(Audio audio) {
        repository.save(audio);
    }

    public List<Audio> getAll() {
        return repository.findAll();
    }

    public Audio foundAudioById(long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new NotFoundDataBaseException("Аудио не найдено");
        }
    }

    public Audio findAudioByName(String name) {
        return repository.findByName(name);
    }

    public void deleteAudio(Long id) {
        repository.deleteById(id);
    }

    public void uploadAudio(String name, MultipartFile file) throws IOException {
        if (Objects.requireNonNull(file.getContentType()).contains("audio")) {
            FileAud newFile = fileService.uploadFile(file);
            Audio newAudio = new Audio(name, newFile);
            repository.save(newAudio);
        }
    }

    public void addTagToAudio(long audioId, long tagId) {
        Audio audio = foundAudioById(audioId);
        audio.setOneTag(tagService.foundTagById(tagId));
        repository.save(audio);
    }
}

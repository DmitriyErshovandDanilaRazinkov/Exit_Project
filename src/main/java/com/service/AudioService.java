package com.service;

import com.model.Audio;
import com.model.Tag;
import com.repository.AudioRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class AudioService {

    private AudioRepository repository;

    private FileService fileService;

    private TagService tagService;

    public AudioService(AudioRepository repository, FileService fileService, TagService tagService) {
        this.repository = repository;
        this.fileService = fileService;
        this.tagService = tagService;
    }

    public boolean saveAudio(Audio audio) {
        repository.save(audio);
        return true;
    }

    public boolean saveAudio(Audio audio, Set<Tag> tags) {

        Audio audioFromDB = repository.findByName(audio.getName());

        if (audioFromDB != null) {
            return false;
        }

        audio.setTags(tags);
        repository.save(audio);
        return true;
    }

    public List<Audio> getAll() {
        return repository.findAll();
    }

    public Audio foundAudioById(long id) throws NotFoundException {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new NotFoundException("Аудио не найдено");
        }
    }

    public Audio foundAudioByName(String name) {
        return repository.findByName(name);
    }

    public boolean deleteAudio(Long id) {
        if (repository.findById(id).isPresent()) {
            fileService.deleteFile(repository.findById(id).get().getFileId());
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean uploadAudio(String name, MultipartFile file) throws IOException {
        if (Objects.requireNonNull(file.getContentType()).equals("audio/mpeg")) {
            Audio newAudio = new Audio(name, fileService.uploadFile(file));
            repository.save(newAudio);
            return true;
        }
        return false;
    }

    public void addTagToAudio(long audioId, long tagId) throws NotFoundException {
        Audio audio = foundAudioById(audioId);
        audio.setOneTag(tagService.foundTagById(tagId));
        repository.save(audio);
    }
}

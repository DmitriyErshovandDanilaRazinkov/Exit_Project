package com.service;

import com.exceptions.NotFoundDataBaseException;
import com.model.Audio;
import com.model.DTO.AudioTo;
import com.model.DTO.FileTo;
import com.model.FileAud;
import com.repository.AudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AudioService {

    private AudioRepository repository;

    private FileService fileService;

    private TagService tagService;

    public void saveAudio(Audio audio) {
        repository.save(audio);
    }

    public List<AudioTo> getAll() {
        return repository.findAll().stream()
                .map(AudioService::audioToAudioTo)
                .collect(Collectors.toList());
    }

    public AudioTo findAudioToById(Long id) {
        return AudioService.audioToAudioTo(findAudioById(id));
    }

    Audio findAudioById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundDataBaseException("Аудио не найдено"));
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
        Audio audio = findAudioById(audioId);
        audio.setOneTag(tagService.findTagById(tagId));
        repository.save(audio);
    }

    public static AudioTo audioToAudioTo(Audio audio) {
        AudioTo audioTo = new AudioTo();
        audioTo.setId(audio.getId());
        audioTo.setName(audio.getName());
        audioTo.setFile(FileService.fileToFileTo(audio.getFile()));
        audioTo.setPremium(audio.isPremium());
        return audioTo;
    }
}

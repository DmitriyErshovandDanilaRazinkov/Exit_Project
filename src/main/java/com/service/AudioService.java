package com.service;

import com.exceptions.DontHaveRightsException;
import com.exceptions.NotFoundDataBaseException;
import com.model.Audio;
import com.model.DTO.AudioTo;
import com.model.DTO.FileTo;
import com.model.FileAud;
import com.model.Tag;
import com.repository.AudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AudioService {

    private AudioRepository repository;

    private FileService fileService;

    private UserService userService;

    private TagService tagService;

    public void saveAudio(Audio audio) {
        repository.save(audio);
    }

    public List<AudioTo> getAll() {
        return repository.findAll().stream()
                .map(AudioService::audioToAudioTo)
                .collect(Collectors.toList());
    }

    public List<AudioTo> getAll(Long tagId) {
        Tag tag = tagService.findTagById(tagId);
        return repository.findAll().stream()
                .filter((audio) -> audio.getTags().contains(tag))
                .map(AudioService::audioToAudioTo)
                .collect(Collectors.toList());
    }

    public List<AudioTo> getAll(String name) {
        return repository.findAllByName(name).stream()
                .map(AudioService::audioToAudioTo)
                .collect(Collectors.toList());
    }

    public List<AudioTo> getTop(int count) {
        return repository.findAll().stream()
                .sorted(Comparator.comparingLong(Audio::getCountListen))
                .limit(count)
                .map(AudioService::audioToAudioTo)
                .collect(Collectors.toList());
    }

    public AudioTo findAudioToById(Long id) {
        return AudioService.audioToAudioTo(findAudioById(id));
    }

    Audio findAudioById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundDataBaseException("Аудио не найдено"));
    }

    public void deleteAudio(Long id) {
        repository.deleteById(id);
    }

    public void uploadAudio(String name, boolean isPremium, MultipartFile file) throws IOException {
        if (Objects.requireNonNull(file.getContentType()).contains("audio") || Objects.requireNonNull(file.getContentType()).contains("video")) {
            FileAud newFile = fileService.uploadFile(file);
            Audio newAudio = new Audio(name, isPremium, newFile);
            repository.save(newAudio);
        }
    }

    public void addTagToAudio(long audioId, long tagId) {
        Audio audio = findAudioById(audioId);
        audio.setOneTag(tagService.findTagById(tagId));
        repository.save(audio);
    }

    public void audioIsListen(Long fileId) {
        Audio audio = repository.findAudioByFile_Id(fileId);
        if (audio.isPremium() && userService.checkUserPremium()) {
            throw new DontHaveRightsException("Вы не можете прослушать этот плейлист");
        }
        audio.setCountListen(audio.getCountListen() + 1);
        tagService.tagsIsListen(audio.getTags());
        saveAudio(audio);
    }

    public static AudioTo audioToAudioTo(Audio audio) {
        AudioTo audioTo = new AudioTo();
        audioTo.setId(audio.getId());
        audioTo.setName(audio.getName());
        audioTo.setCountListen(audio.getCountListen());
        audioTo.setFile(FileService.fileToFileTo(audio.getFile()));
        audioTo.setPremium(audio.isPremium());
        audioTo.setTags(audio.getTags().stream()
                .map(TagService::tagToTagTo)
                .collect(Collectors.toList()));
        return audioTo;
    }

}
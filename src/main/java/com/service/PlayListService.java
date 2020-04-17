package com.service;

import com.model.Audio;
import com.model.PlayList;
import com.model.User;
import com.repository.PlayListRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayListService {

    private PlayListRepository repository;

    private UserService userService;

    private AudioService audioService;

    public PlayListService(PlayListRepository repository, UserService userService, AudioService audioService) {
        this.repository = repository;
        this.userService = userService;
        this.audioService = audioService;
    }

    public boolean addPlayList(long ownerId, String name, boolean isPrivate) throws NotFoundException {
        User owner = userService.findUserById(ownerId);
        PlayList newPlayList = new PlayList(name, isPrivate);

        newPlayList.setOwner(owner);
        owner.addPlayList(newPlayList);

        repository.save(newPlayList);
        userService.saveUser(owner);

        return true;
    }

    public boolean setPrivate(long id, boolean newPrivate) throws NotFoundException {
        if (repository.findById(id).isPresent()) {
            repository.findById(id).get().setPrivate(newPrivate);
            return true;
        } else {
            throw new NotFoundException("ПлейЛист не найден");
        }
    }

    public boolean addAudio(long id, long audioId) throws NotFoundException {
        if (repository.findById(id).isPresent()) {

            Audio nowAudio = audioService.foundAudioById(audioId);
            PlayList nowPlayList = repository.findById(id).get();

            nowPlayList.getListAudio().add(nowAudio);

            nowAudio.getPlayLists().add(nowPlayList);

            repository.save(nowPlayList);
            audioService.saveAudio(nowAudio);

            return true;
        } else {
            throw new NotFoundException("ПлейЛист не найден");
        }
    }

    public boolean deleteAudioFromPlayList(long id, long audioId) throws NotFoundException {
        if (repository.findById(id).isPresent()) {
            repository.findById(id).get().getListAudio().remove(audioService.foundAudioById(audioId));
            return true;
        } else {
            throw new NotFoundException("ПлейЛист не найден");
        }
    }

    public List<PlayList> getAll() {
        return repository.findAll();
    }

    public PlayList foundPlayListById(long id) throws NotFoundException {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new NotFoundException("ПлейЛист не найден");
        }
    }

    public boolean deletePlayList(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}

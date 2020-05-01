package com.service;

import com.exceptions.NotFoundDataBaseException;
import com.model.*;
import com.repository.PlayListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayListService {

    private PlayListRepository repository;

    private UserService userService;

    private AudioService audioService;

    private RoleInPlayListsService roleInPlayListsService;

    public PlayListService(PlayListRepository repository, UserService userService, AudioService audioService, RoleInPlayListsService roleInPlayListsService) {
        this.repository = repository;
        this.userService = userService;
        this.audioService = audioService;
        this.roleInPlayListsService = roleInPlayListsService;
    }

    public boolean savePlayList(PlayList playList) {
        repository.save(playList);
        return true;
    }

    public boolean addPlayList(long ownerId, String name, boolean isPrivate) throws NotFoundDataBaseException {
        User owner = userService.findUserById(ownerId);
        PlayList newPlayList = new PlayList(name, isPrivate);

        RoleInPlayList role = new RoleInPlayList();
        role.setPlayList(newPlayList);
        role.setUser(owner);
        role.setPlayListRole(Role_PlayList.ROLE_OWNER);

        roleInPlayListsService.saveRole(role);

        newPlayList.getUsers().add(owner);
        newPlayList.getRoleInPlayLists().add(role);

        savePlayList(newPlayList);

        owner.getPlayLists().add(newPlayList);
        owner.getRoleInPlayLists().put(newPlayList.getId(), role);

        userService.saveUser(owner);
        repository.save(newPlayList);
        roleInPlayListsService.saveRole(role);

        return true;
    }

    public boolean setPrivate(long id, boolean newPrivate) throws NotFoundDataBaseException {
        if (repository.findById(id).isPresent()) {
            repository.findById(id).get().setPrivate(newPrivate);
            return true;
        } else {
            throw new NotFoundDataBaseException("ПлейЛист не найден");
        }
    }

    public boolean addAudio(long id, long audioId) throws NotFoundDataBaseException {
        if (repository.findById(id).isPresent()) {

            Audio nowAudio = audioService.foundAudioById(audioId);
            PlayList nowPlayList = repository.findById(id).get();

            nowPlayList.getListAudio().add(nowAudio);

            nowAudio.getPlayLists().add(nowPlayList);

            repository.save(nowPlayList);
            audioService.saveAudio(nowAudio);

            return true;
        } else {
            throw new NotFoundDataBaseException("ПлейЛист не найден");
        }
    }

    public boolean deleteAudioFromPlayList(long id, long audioId) throws NotFoundDataBaseException {
        if (repository.findById(id).isPresent()) {
            repository.findById(id).get().getListAudio().remove(audioService.foundAudioById(audioId));
            return true;
        } else {
            throw new NotFoundDataBaseException("ПлейЛист не найден");
        }
    }

    public List<PlayList> getAll() {
        return repository.findAll();
    }

    public PlayList findPlayListById(long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        } else {
            throw new NotFoundDataBaseException("ПлейЛист не найден");
        }
    }

    public boolean addNewUserToPlayList(Long playListId, Long userId) {
        User user = userService.findUserById(userId);
        PlayList nowPlayList = findPlayListById(playListId);

        RoleInPlayList role = new RoleInPlayList();
        role.setPlayList(nowPlayList);
        role.setUser(user);
        role.setPlayListRole(Role_PlayList.ROLE_USER);

        roleInPlayListsService.saveRole(role);

        nowPlayList.getUsers().add(user);
        nowPlayList.getRoleInPlayLists().add(role);

        user.getPlayLists().add(nowPlayList);
        user.getRoleInPlayLists().put(nowPlayList.getId(), role);

        userService.saveUser(user);
        repository.save(nowPlayList);
        roleInPlayListsService.saveRole(role);

        return true;
    }

    public boolean deleteUser(Long playListId, Long userId) {
        User user = userService.findUserById(userId);
        PlayList nowPlayList = findPlayListById(playListId);

        RoleInPlayList role = user.getRoleInPlayLists().get(playListId);

        role.setPlayListRole(null);
        role.setPlayList(null);
        role.setUser(null);

        roleInPlayListsService.saveRole(role);

        nowPlayList.getUsers().remove(user);
        nowPlayList.getRoleInPlayLists().remove(role);

        user.getPlayLists().remove(nowPlayList);
        user.getRoleInPlayLists().remove(nowPlayList.getId());

        userService.saveUser(user);
        repository.save(nowPlayList);
        roleInPlayListsService.deleteRole(role);

        return true;
    }

    public boolean deletePlayList(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean checkUser(PlayList nowPlayList, Long userId) {
        return userService.findUserById(userId).getPlayLists().contains(nowPlayList);
    }
}

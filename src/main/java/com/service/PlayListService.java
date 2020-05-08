package com.service;

import com.exceptions.DontHaveRightsException;
import com.exceptions.NotFoundDataBaseException;
import com.model.*;
import com.repository.PlayListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

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

    public boolean addPlayList(long ownerId, String name, boolean isPrivate) {
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

        newPlayList.setJoinCode(newPlayList.getId() + "" + randomUUID().toString().substring(0, 8));

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
        return addNewUserToPlayList(findPlayListById(playListId), userId);
    }

    public boolean addNewUserToPlayList(PlayList nowPlayList, Long userId) {
        User user = userService.findUserById(userId);

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

    public Long joinUser(String joinCode, Long userId) {
        PlayList playList = repository.findByJoinCode(joinCode);
        addNewUserToPlayList(playList.getId(), userId);
        repository.save(playList);
        return playList.getId();
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

    public List<RoleInPlayList> getListWithAndUnderRole(Long id, Role_PlayList role) {
        return findPlayListById(id).getRoleInPlayLists().stream()
                .filter((RoleInPlayList role1) -> role1.getPlayListRole().compareUnder(role))
                .sorted(Role_PlayList::compareTo)
                .collect(Collectors.toList());
    }

    public boolean changeUserRoleTo(Long playlistId, Long userId, Role_PlayList role_playList) {

        RoleInPlayList role = userService.findUserById(userId).getRoleInPlayLists().get(playlistId);

        if (role.getPlayListRole() == Role_PlayList.ROLE_OWNER) {
            throw new DontHaveRightsException("Никто не может влиять на владельца");
        }

        role.setPlayListRole(role_playList);

        roleInPlayListsService.saveRole(role);

        return true;
    }
}

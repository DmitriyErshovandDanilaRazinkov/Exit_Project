package com.service;

import com.exceptions.DontHaveRightsException;
import com.exceptions.NotFoundDataBaseException;
import com.model.*;
import com.model.DTO.AudioTo;
import com.model.DTO.PlayListTo;
import com.model.DTO.RoleInPlayListTo;
import com.model.composite_key.RoleInPlayListId;
import com.model.enums.Role_PlayList;
import com.repository.PlayListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@AllArgsConstructor
@Service
public class PlayListService {

    private PlayListRepository repository;

    private UserService userService;

    private AudioService audioService;

    private RoleInPlayListsService roleInPlayListsService;

    public void addPlayList(Long ownerId, String name, boolean isPrivate) {
        User owner = userService.findUserById(ownerId);

        PlayList newPlayList = new PlayList(name, isPrivate);
        repository.save(newPlayList);
        newPlayList.setJoinCode(newPlayList.getId() + "" + randomUUID().toString().substring(0, 8));

        RoleInPlayList role = new RoleInPlayList(new RoleInPlayListId(owner, newPlayList));
        role.setPlayListRole(Role_PlayList.ROLE_OWNER);
        roleInPlayListsService.saveRole(role);

        newPlayList.getRoleInPlayLists().add(role);
        repository.save(newPlayList);

        owner.getRoleInPlayLists().add(role);

        userService.saveUser(owner);
        repository.save(newPlayList);
        roleInPlayListsService.saveRole(role);
    }


    public void addAudio(long id, long audioId) {
        if (repository.findById(id).isPresent()) {

            Audio nowAudio = audioService.findAudioById(audioId);
            PlayList nowPlayList = repository.findById(id).get();

            nowPlayList.getListAudio().add(nowAudio);

            nowAudio.getPlayLists().add(nowPlayList);

            repository.save(nowPlayList);
            audioService.saveAudio(nowAudio);
        } else {
            throw new NotFoundDataBaseException("ПлейЛист не найден");
        }
    }

    public Set<RoleInPlayListTo> getRoleInPlayLists(Long id) {
        return findPlayListById(id).getRoleInPlayLists().stream()
                .map(RoleInPlayListsService::roleInPlayListToDto)
                .collect(Collectors.toSet());
    }

    public void deleteAudioFromPlayList(long id, long audioId) {
        repository.findById(id).orElseThrow(() -> new NotFoundDataBaseException("ПлейЛист не найден"))
                .getListAudio().remove(audioService.findAudioById(audioId));
    }

    public List<PlayListTo> getAll() {
        return repository.findAll().stream()
                .map(PlayListService::playListToPlayListTo)
                .collect(Collectors.toList());
    }

    public PlayListTo findPlayListToById(Long id) {
        return playListToPlayListTo(findPlayListById(id));
    }

    PlayList findPlayListById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundDataBaseException("ПлейЛист не найден"));
    }

    public void addNewUserToPlayList(Long playListId, Long userId) {
        addNewUserToPlayList(findPlayListById(playListId), userId);
    }

    public void addNewUserToPlayList(PlayList nowPlayList, Long userId) {
        User user = userService.findUserById(userId);

        RoleInPlayList role = new RoleInPlayList(new RoleInPlayListId(user, nowPlayList));
        role.setPlayListRole(Role_PlayList.ROLE_USER);

        roleInPlayListsService.saveRole(role);
        nowPlayList.getRoleInPlayLists().add(role);

        user.getRoleInPlayLists().add(role);

        userService.saveUser(user);
        repository.save(nowPlayList);
        roleInPlayListsService.saveRole(role);
    }

    public Long joinUser(String joinCode, Long userId) {
        PlayList playList = repository.findByJoinCode(joinCode);
        if (roleInPlayListsService.getUserRoleInPlayList(userId, playList.getId())
                .getPlayListRole().compareUnder(Role_PlayList.ROLE_NONE)) {
            addNewUserToPlayList(playList.getId(), userId);
            repository.save(playList);
        }
        return playList.getId();
    }

    public void deleteUser(Long playListId, Long userId) {
        RoleInPlayList role = roleInPlayListsService.getUserRoleInPlayList(userId, playListId);

        if (role.getPlayListRole() == Role_PlayList.ROLE_OWNER) {
            throw new DontHaveRightsException("Никто не может удалить владельца");
        }

        roleInPlayListsService.deleteRole(role);
    }

    public void deletePlayList(Long id) {
        repository.deleteById(id);
    }

    public List<RoleInPlayListTo> getListWithAndUnderRole(Long playListId, Role_PlayList role) {
        return roleInPlayListsService.getUserWithRoleWereRoleUnder(playListId, role);
    }

    public void changeUserRoleTo(Long playlistId, Long userId, Role_PlayList role_playList) {

        RoleInPlayList role = roleInPlayListsService.getUserRoleInPlayList(userId, playlistId);

        if (role.getPlayListRole() == Role_PlayList.ROLE_OWNER) {
            throw new DontHaveRightsException("Никто не может влиять на владельца");
        }

        role.setPlayListRole(role_playList);

        roleInPlayListsService.saveRole(role);
    }

    public List<PlayListTo> getPlayListsByUser(Long id) {
        return repository.getAllByWithPlayList(id).stream()
                .map(PlayListService::playListToPlayListTo)
                .collect(Collectors.toList());
    }

    public static PlayListTo playListToPlayListTo(PlayList playList) {
        PlayListTo playListTo = new PlayListTo();
        playListTo.setId(playList.getId());
        playListTo.setName(playList.getName());
        playListTo.setJoinCode(playList.getJoinCode());
        playListTo.setPrivate(playList.isPrivate());
        return playListTo;
    }

    public List<AudioTo> getPlayListAudios(Long id) {
        return findPlayListById(id).getListAudio().stream()
                .map(AudioService::audioToAudioTo)
                .collect(Collectors.toList());
    }
}

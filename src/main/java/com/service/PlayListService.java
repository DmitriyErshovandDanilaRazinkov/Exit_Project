package com.service;

import com.exceptions.DontHaveRightsException;
import com.exceptions.NotFoundDataBaseException;
import com.model.*;
import com.repository.PlayListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;

@AllArgsConstructor
@Service
public class PlayListService {

    private PlayListRepository repository;

    private UserService userService;

    private AudioService audioService;

    private RoleInPlayListsService roleInPlayListsService;

    public void savePlayList(PlayList playList) {
        repository.save(playList);
    }

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

            Audio nowAudio = audioService.foundAudioById(audioId);
            PlayList nowPlayList = repository.findById(id).get();

            nowPlayList.getListAudio().add(nowAudio);

            nowAudio.getPlayLists().add(nowPlayList);

            repository.save(nowPlayList);
            audioService.saveAudio(nowAudio);
        } else {
            throw new NotFoundDataBaseException("ПлейЛист не найден");
        }
    }

    public void deleteAudioFromPlayList(long id, long audioId) {
        if (repository.findById(id).isPresent()) {
            repository.findById(id).get().getListAudio().remove(audioService.foundAudioById(audioId));
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
        roleInPlayListsService.deleteRole(roleInPlayListsService.getUserRoleInPlayList(userId, playListId));
    }

    public void deletePlayList(Long id) {
        repository.deleteById(id);
    }

    public List<RoleInPlayList> getListWithAndUnderRole(Long id, Role_PlayList role) {
        return findPlayListById(id).getRoleInPlayLists().stream()
                .filter((RoleInPlayList role1) -> role1.getPlayListRole().compareUnder(role))
                .sorted(Role_PlayList::compareTo)
                .collect(Collectors.toList());
    }

    public void changeUserRoleTo(Long playlistId, Long userId, Role_PlayList role_playList) {

        RoleInPlayList role = roleInPlayListsService.getUserRoleInPlayList(userId, playlistId);

        if (role.getPlayListRole() == Role_PlayList.ROLE_OWNER) {
            throw new DontHaveRightsException("Никто не может влиять на владельца");
        }

        role.setPlayListRole(role_playList);

        roleInPlayListsService.saveRole(role);
    }

    public List<PlayList> getPlayListsByUser(Long id) {
        return repository.getAllByWithPlayList(id);
    }
}

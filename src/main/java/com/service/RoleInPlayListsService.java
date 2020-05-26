package com.service;

import com.model.DTO.RoleInPlayListTo;
import com.model.RoleInPlayList;
import com.model.composite_key.RoleInPlayListId;
import com.model.enums.Role_PlayList;
import com.repository.RoleInPlayListRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RoleInPlayListsService {

    private RoleInPlayListRepository repository;

    void saveRole(RoleInPlayList roleInPlayList) {
        repository.save(roleInPlayList);
    }

    void deleteRole(RoleInPlayList roleInPlayList) {
        repository.delete(roleInPlayList);
    }

    public RoleInPlayListTo getToUserRoleInPlayList(Long userId, Long playListId) {
        return roleInPlayListToDto(getUserRoleInPlayList(userId, playListId));
    }

    public RoleInPlayListTo getToUserRoleInPlayList(RoleInPlayListId id) {
        return roleInPlayListToDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Роль не найдена")));
    }

    public List<RoleInPlayListTo> getAllTo() {
        return repository.findAll().stream()
                .map(RoleInPlayListsService::roleInPlayListToDto)
                .collect(Collectors.toList());
    }

    RoleInPlayList getUserRoleInPlayList(Long userId, Long playListId) {
        return repository.findByUser_idAndPlayList_id(userId, playListId).orElseGet(() -> {
            RoleInPlayList role = new RoleInPlayList();
            role.setPlayListRole(Role_PlayList.ROLE_NONE);
            return role;
        });
    }

    public List<RoleInPlayListTo> getUserWithRoleWereRoleUnder(Long playListId, Role_PlayList role) {
        return repository.getRoleWhereRoleUnder(playListId, role).stream()
                .map(RoleInPlayListsService::roleInPlayListToDto)
                .collect(Collectors.toList());
    }

    public static RoleInPlayListTo roleInPlayListToDto(RoleInPlayList role) {
        RoleInPlayListTo roleTo = new RoleInPlayListTo();
        roleTo.setPlayList(PlayListService.playListToPlayListTo(role.getIdComp().getPlayList()));
        roleTo.setUser(UserService.userToUserDetailsTo(role.getIdComp().getUser()));
        roleTo.setPlayListRole(role.getPlayListRole());
        return roleTo;
    }

    public void deleteRoleInPlayList(RoleInPlayListId id) {
        repository.deleteById(id);
    }
}

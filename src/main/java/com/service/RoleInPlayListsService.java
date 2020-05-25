package com.service;

import com.model.DTO.RoleInPlayListTo;
import com.model.RoleInPlayList;
import com.model.Role_PlayList;
import com.repository.RoleInPlayListRepository;
import lombok.AllArgsConstructor;
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
}

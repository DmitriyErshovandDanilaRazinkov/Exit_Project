package com.service;

import com.model.RoleInPlayList;
import com.model.Role_PlayList;
import com.repository.RoleInPlayListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleInPlayListsService {

    private RoleInPlayListRepository repository;

    public void saveRole(RoleInPlayList roleInPlayList) {
        repository.save(roleInPlayList);
    }

    public void deleteRole(RoleInPlayList roleInPlayList) {
        repository.delete(roleInPlayList);
    }

    public RoleInPlayList getUserRoleInPlayList(Long userId, Long playListId) {
        return repository.findByUser_idAndPlayList_id(userId, playListId).orElseGet(() -> {
            RoleInPlayList role = new RoleInPlayList();
            role.setPlayListRole(Role_PlayList.ROLE_NONE);
            return role;
        });
    }

    public List<RoleInPlayList> getUserWithRoleWereRoleUnder(Long playListId, Role_PlayList role) {
        return repository.getRoleWhereRoleUnder(playListId, role);
    }
}

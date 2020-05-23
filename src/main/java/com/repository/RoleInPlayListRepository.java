package com.repository;


import com.model.RoleInPlayList;
import com.model.RoleInPlayListId;
import com.model.Role_PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleInPlayListRepository extends JpaRepository<RoleInPlayList, RoleInPlayListId> {

    RoleInPlayList findByIdComp(RoleInPlayListId idComp);

    @Query("select r from RoleInPlayList r where r.idComp.playList.id = :playList and r.idComp.user.id = :user")
    Optional<RoleInPlayList> findByUser_idAndPlayList_id(@Param("user") Long userId, @Param("playList") Long playListId);

    @Query("select r from RoleInPlayList r where r.idComp.playList.id = :playList and r.playListRole <= :role")
    List<RoleInPlayList> getRoleWhereRoleUnder(@Param("playList") Long userId, @Param("role") Role_PlayList role);
}

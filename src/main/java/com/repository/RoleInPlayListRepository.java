package com.repository;


import com.model.RoleInPlayList;
import com.model.RoleInPlayListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleInPlayListRepository extends JpaRepository<RoleInPlayList, RoleInPlayListId> {

    RoleInPlayList findByIdComp(RoleInPlayListId idComp);

    @Query("select r from RoleInPlayList r where r.idComp.playList.id = :playList and r.idComp.user.id = :user")
    Optional<RoleInPlayList> findByUser_idAndPlayList_id(@Param("user") Long userId, @Param("playList") Long playListId);
}

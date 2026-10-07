package com.notes.sharing.repository;

import com.notes.sharing.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f FROM Friendship f WHERE f.fUser.userID = :userId OR f.sUser.userID = :userId")
    List<Friendship> findForUser(@Param("userId") Long userId);

    @Query("DELETE FROM Friendship f WHERE f.fUser.userID = :userId OR f.sUser.userID = :userId")
    @Modifying
    void deleteByUser(@Param("userId") Long userId);
}

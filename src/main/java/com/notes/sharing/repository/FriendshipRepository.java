package com.notes.sharing.repository;

import com.notes.sharing.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByFUser_UserIDOrSUser_UserID(Long fUserId, Long sUserId);
}

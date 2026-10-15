package com.notes.sharing.service;

import com.notes.sharing.dto.FriendshipResponse;
import com.notes.sharing.entity.Friendship;
import com.notes.sharing.entity.Notification;
import com.notes.sharing.entity.User;
import com.notes.sharing.mapper.UserMapper;
import com.notes.sharing.exception.ErrorCode;
import com.notes.sharing.exception.SystemException;
import com.notes.sharing.repository.FriendshipRepository;
import com.notes.sharing.repository.NotificationRepository;
import com.notes.sharing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final UserMapper userMapper;

    @Transactional
    public FriendshipResponse sendRequest(Long senderId, String targetUsername) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new SystemException(ErrorCode.USER_NOT_FOUND, "User not found"));
        User target = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new SystemException(ErrorCode.USER_NOT_FOUND, "User not found: " + targetUsername));

        if (sender.getUserID().equals(target.getUserID())) {
            throw new SystemException(ErrorCode.SELF_ACTION, "Cannot add yourself");
        }
        boolean exists = friendshipRepository.findForUser(senderId).stream()
                .anyMatch(f -> (f.getFUser().getUserID().equals(target.getUserID())
                        || f.getSUser().getUserID().equals(target.getUserID()))
                        && !"rejected".equalsIgnoreCase(f.getStatus()));
        if (exists) {
            throw new SystemException(ErrorCode.REQUEST_INVALID, "Friend request already exists");
        }

        Friendship friendship = Friendship.builder()
                .fUser(sender)
                .sUser(target)
                .status("pending")
                .build();
        Friendship saved = friendshipRepository.save(friendship);
        notificationRepository.save(Notification.builder()
                .notifier(sender)
                .notified(target)
                .type("friend_request")
                .friendship(saved)
                .build());
        return userMapper.toFriendship(saved, senderId);
    }

    @Transactional(readOnly = true)
    public List<FriendshipResponse> myFriends(Long userId) {
        List<FriendshipResponse> out = new ArrayList<>();
        for (Friendship f : friendshipRepository.findForUser(userId)) {
            if ("accepted".equalsIgnoreCase(f.getStatus())) {
                out.add(userMapper.toFriendship(f, userId));
            }
        }
        return out;
    }

    @Transactional(readOnly = true)
    public List<FriendshipResponse> pendingReceived(Long userId) {
        List<FriendshipResponse> out = new ArrayList<>();
        for (Friendship f : friendshipRepository.findForUser(userId)) {
            if ("pending".equalsIgnoreCase(f.getStatus()) && f.getSUser().getUserID().equals(userId)) {
                out.add(userMapper.toFriendship(f, userId));
            }
        }
        return out;
    }

    @Transactional
    public void respond(Long relationshipId, Long userId, boolean accept) {
        Friendship f = friendshipRepository.findById(relationshipId)
                .orElseThrow(() -> new SystemException(ErrorCode.FRIENDSHIP_NOT_FOUND, "Request not found"));

        if (!f.getSUser().getUserID().equals(userId)) {
            throw new SystemException(ErrorCode.FRIENDSHIP_DENIED, "Only the receiver can answer this request");
        }
        if (!"pending".equalsIgnoreCase(f.getStatus())) {
            throw new SystemException(ErrorCode.REQUEST_INVALID, "Request already decided");
        }
        f.setStatus(accept ? "accepted" : "rejected");
        friendshipRepository.save(f);
        if (accept) {
            notificationRepository.save(Notification.builder()
                    .notifier(f.getSUser())
                    .notified(f.getFUser())
                    .type("friend_accept")
                    .friendship(f)
                    .build());
        }
    }

    @Transactional
    public void remove(Long relationshipId, Long userId) {
        Friendship f = friendshipRepository.findById(relationshipId)
                .orElseThrow(() -> new SystemException(ErrorCode.FRIENDSHIP_NOT_FOUND, "Request not found"));

        if (!f.getFUser().getUserID().equals(userId) && !f.getSUser().getUserID().equals(userId)) {
            throw new SystemException(ErrorCode.FRIENDSHIP_DENIED, "You are not part of this friendship");
        }
        notificationRepository.deleteByFriendship(relationshipId);
        friendshipRepository.delete(f);
    }

}

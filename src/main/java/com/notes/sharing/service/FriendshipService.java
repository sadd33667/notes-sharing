package com.notes.sharing.service;

import com.notes.sharing.dto.FriendshipResponse;
import com.notes.sharing.entity.Friendship;
import com.notes.sharing.entity.Notification;
import com.notes.sharing.entity.User;
import com.notes.sharing.exception.NotFoundException;
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

    @Transactional
    public FriendshipResponse sendRequest(Long senderId, String targetUsername) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        User target = userRepository.findByUsername(targetUsername)
                .orElseThrow(() -> new NotFoundException("User not found: " + targetUsername));

        if (sender.getUserID().equals(target.getUserID())) {
            throw new IllegalArgumentException("Cannot add yourself");
        }
        boolean exists = friendshipRepository.findForUser(senderId).stream()
                .anyMatch(f -> (f.getFUser().getUserID().equals(target.getUserID())
                        || f.getSUser().getUserID().equals(target.getUserID()))
                        && !"rejected".equalsIgnoreCase(f.getStatus()));
        if (exists) {
            throw new IllegalArgumentException("Friend request already exists");
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
        return toResponse(saved, senderId);
    }

    public List<FriendshipResponse> myFriends(Long userId) {
        List<FriendshipResponse> out = new ArrayList<>();
        for (Friendship f : friendshipRepository.findForUser(userId)) {
            if ("accepted".equalsIgnoreCase(f.getStatus())) {
                out.add(toResponse(f, userId));
            }
        }
        return out;
    }

    public List<FriendshipResponse> pendingReceived(Long userId) {
        List<FriendshipResponse> out = new ArrayList<>();
        for (Friendship f : friendshipRepository.findForUser(userId)) {
            if ("pending".equalsIgnoreCase(f.getStatus()) && f.getSUser().getUserID().equals(userId)) {
                out.add(toResponse(f, userId));
            }
        }
        return out;
    }

    @Transactional
    public void respond(Long relationshipId, Long userId, boolean accept) {
        Friendship f = friendshipRepository.findById(relationshipId)
                .orElseThrow(() -> new NotFoundException("Request not found"));

        if (!f.getSUser().getUserID().equals(userId)) {
            throw new NotFoundException("Request not found");
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
                .orElseThrow(() -> new NotFoundException("Request not found"));

        if (!f.getFUser().getUserID().equals(userId) && !f.getSUser().getUserID().equals(userId)) {
            throw new NotFoundException("Request not found");
        }
        notificationRepository.deleteByFriendship(relationshipId);
        friendshipRepository.delete(f);
    }

    private static FriendshipResponse toResponse(Friendship f, Long viewerId) {
        User other = f.getFUser().getUserID().equals(viewerId) ? f.getSUser() : f.getFUser();
        return FriendshipResponse.builder()
                .relationshipID(f.getRelationshipID())
                .friendUsername(other.getUsername())
                .status(f.getStatus())
                .build();
    }
}

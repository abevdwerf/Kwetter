package com.kwetter.userservice;

import com.kwetter.userservice.messaging.UserMessageProducer;
import com.kwetter.userservice.request.UserRegistrationRequest;
import com.kwetter.userservice.request.UserUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMessageProducer UserMessageProducer;

    public void registerUser(UserRegistrationRequest request) {

        if (request.firebaseUuid() == null || request.userName() == null || request.displayName() == null) {
            throw new IllegalArgumentException("Firebase UUID, username, and display name are required");
        }

        // Check if the Firebase UUID is unique
        if (userRepository.existsByFirebaseUuid(request.firebaseUuid())) {
            throw new RuntimeException("Firebase UUID already exists");
        }

        User user = User.builder()
                .userName(request.userName())
                .displayName(request.displayName())
                .description(request.description())
                .firebaseUuid(request.firebaseUuid())
                .build();
        userRepository.save(user);
    }

    public void updateUser(UserUpdateRequest request) {
        int userId = request.id();

        // Retrieve the existing user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getDisplayName().equals(request.displayName()) && request.displayName() != null) {
            user.setDisplayName(request.displayName());
            UserMessageProducer.sendMessage(user);
        }
        if (!user.getUserName().equals(request.userName()) && request.userName() != null)
            user.setUserName(request.userName());

        user.setDescription(request.description());

        userRepository.save(user);
    }

    public User getUserByFirebaseUuid(String firebaseUuid) {
        return userRepository.findByFirebaseUuid(firebaseUuid)
                .orElseThrow(() -> new EntityNotFoundException("User not found with Firebase UUID: " + firebaseUuid));
    }
}

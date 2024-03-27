package com.kwetter.userservice;

import com.kwetter.userservice.messaging.UserMessageProducer;
import com.kwetter.userservice.request.UserRegistrationRequest;
import com.kwetter.userservice.request.UserUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMessageProducer UserMessageProducer;

    public void registerUser(UserRegistrationRequest request) {
        User user = User.builder()
                .userName(request.userName())
                .displayName(request.displayName())
                .description(request.description())
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
}

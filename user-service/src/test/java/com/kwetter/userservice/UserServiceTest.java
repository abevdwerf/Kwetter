package com.kwetter.userservice;

import com.kwetter.userservice.messaging.UserMessageProducer;
import com.kwetter.userservice.request.UserRegistrationRequest;
import com.kwetter.userservice.request.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMessageProducer userMessageProducer;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser() {
        UserRegistrationRequest request = new UserRegistrationRequest("username", "displayname", "description");

        userService.registerUser(request);

        // Verify that save method is called with correct user object
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser() {
        // Mock existing user
        User existingUser = new User(1, "existing_displayname", "existing_username", "existing_description");
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(existingUser));

        UserUpdateRequest request = new UserUpdateRequest(1, "existing_displayname", "existing_username", "new_description");

        userService.updateUser(request);

        // Verify that save method is called with correct user object
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        // Mock user not found scenario
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        UserUpdateRequest request = new UserUpdateRequest(1, "new_username", "new_displayname", "new_description");

        // Ensure that IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(request));

        // Verify that save method is never called
        verify(userRepository, never()).save(any(User.class));
    }
}

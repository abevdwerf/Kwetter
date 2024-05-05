package com.kwetter.userservice;

import com.kwetter.userservice.request.UserRegistrationRequest;
import com.kwetter.userservice.request.UserUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        userService.registerUser(userRegistrationRequest);
    }

    @PutMapping
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userService.updateUser(request);
    }

    @GetMapping("/{firebaseUuid}")
    public ResponseEntity<User> getUserByFirebaseUuid(@PathVariable String firebaseUuid) {
        User user = userService.getUserByFirebaseUuid(firebaseUuid);
        return ResponseEntity.ok(user);
    }

    //remove user
}

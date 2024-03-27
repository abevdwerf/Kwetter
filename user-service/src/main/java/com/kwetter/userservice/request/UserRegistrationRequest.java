package com.kwetter.userservice.request;

public record UserRegistrationRequest(
        String displayName,
        String userName,
        String description) {
}

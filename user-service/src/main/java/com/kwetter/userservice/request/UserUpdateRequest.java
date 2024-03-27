package com.kwetter.userservice.request;

public record UserUpdateRequest(
        int id,
        String displayName,
        String userName,
        String description) {
}

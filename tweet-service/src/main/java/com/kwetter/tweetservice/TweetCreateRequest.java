package com.kwetter.tweetservice;

public record TweetCreateRequest(
        String userId,
        String displayName,
        String content) {
}

package com.kwetter.tweetservice;

import com.kwetter.tweetservice.dto.UserMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TweetService {

    private final TweetRepository tweetRepository;

    private boolean isContentLengthValid(String content) {
        return content.length() <= 140;
    }

    public void createTweet(TweetCreateRequest request) {
        String content = request.content();

        // Check if content length is within limits
        if (!isContentLengthValid(content)) {
            throw new IllegalArgumentException("Tweet content exceeds maximum length of 140 characters.");
        }

        Tweet tweet = Tweet.builder().
                userId(request.userId()).
                displayName(request.displayName()).
                content(request.content())
                .build();
        tweetRepository.save(tweet);
    }

    public void updateDisplayName(UserMessage userMessage) {
        List<Tweet> tweetsToUpdate = getTweetsByUserId(userMessage.getId());
        for (Tweet tweet : tweetsToUpdate) {
            tweet.setDisplayName(userMessage.getDisplayName());
            tweetRepository.save(tweet);
        }
    }

    public List<Tweet> getTweetsByUserId(String userId) {
        return tweetRepository.findByUserId(userId);
    }
}

package com.kwetter.tweetservice;

import com.kwetter.tweetservice.dto.UserMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Tag("unitTest")
public class TweetServiceTest {
    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetService tweetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateTweetWithValidContent() {
        TweetCreateRequest request = new TweetCreateRequest("userId", "displayName", "Valid content");
        tweetService.createTweet(request);
        verify(tweetRepository, times(1)).save(any());
    }

    @Test
    public void testCreateTweetWithInvalidContent() {
        String longContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. This is a bit more than 140 characters.";
        TweetCreateRequest request = new TweetCreateRequest("userId", "displayName", longContent);
        assertThrows(IllegalArgumentException.class, () -> tweetService.createTweet(request));
    }

    @Test
    public void testUpdateDisplayName() {
        UserMessage userMessage = new UserMessage("userId", "displayName", "Name");
        when(tweetRepository.findByUserId(userMessage.getId())).thenReturn(Collections.singletonList(new Tweet()));
        tweetService.updateDisplayName(userMessage);
        verify(tweetRepository, times(1)).save(any());
    }
}
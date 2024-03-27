package com.kwetter.tweetservice;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tweets")
@AllArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @PostMapping
    public void createTweet(@RequestBody TweetCreateRequest request) {
        tweetService.createTweet(request);
    }

    @GetMapping(value = "/user/{userId}")
    public List<Tweet> getTweetsByUserId(@PathVariable String userId) {
        return tweetService.getTweetsByUserId(userId);
    }
}

package com.kwetter.tweetservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

@DataMongoTest
@AutoConfigureDataMongo
public class TweetRepositoryTests {

    @Autowired
    private TweetRepository tweetRepository;

    @Test
    public void testFindByUserIdNotNull() {
        Tweet tweet = Tweet.builder().
                userId("123").
                displayName("John Doe").
                content("test content")
                .build();

        tweetRepository.save(tweet);

        List<Tweet> tweetList = tweetRepository.findByUserId(tweet.getUserId());

        Assertions.assertThat(tweetList).isNotNull();

    }
}

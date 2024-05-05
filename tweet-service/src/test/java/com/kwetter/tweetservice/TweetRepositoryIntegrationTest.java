package com.kwetter.tweetservice;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
@Testcontainers
class TweetRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private TweetRepository tweetRepository;

    @Test
    void connectionEstablished() {
        assertThat(mongoDBContainer.isCreated()).isTrue();
        assertThat(mongoDBContainer.isRunning()).isTrue();
    }

    @Test
    void testFindByUserIdNotNull() {
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

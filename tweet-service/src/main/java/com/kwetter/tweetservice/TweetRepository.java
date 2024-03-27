package com.kwetter.tweetservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {

    List<Tweet> findByUserId(String id);
}

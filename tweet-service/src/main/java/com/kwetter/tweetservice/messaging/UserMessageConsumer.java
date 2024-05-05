package com.kwetter.tweetservice.messaging;

import com.kwetter.tweetservice.TweetService;
import com.kwetter.tweetservice.dto.UserMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.kwetter.tweetservice.messaging.RabbitMQConfiguration.QUEUE_NAME;

@Component
public class UserMessageConsumer {
    private final TweetService tweetService;

    public UserMessageConsumer(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void consumeMessage(UserMessage userMessage) {
        tweetService.updateDisplayName(userMessage);
    }
}

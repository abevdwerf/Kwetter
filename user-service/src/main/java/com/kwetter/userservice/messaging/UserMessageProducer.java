package com.kwetter.userservice.messaging;

import com.kwetter.userservice.User;
import com.kwetter.userservice.dto.UserMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.kwetter.userservice.messaging.RabbitMQConfiguration.*;

@Service
public class UserMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public UserMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(User user) {
        UserMessage userMessage = new UserMessage(user.getId(), user.getDisplayName(), user.getUserName());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, userMessage);
    }
}

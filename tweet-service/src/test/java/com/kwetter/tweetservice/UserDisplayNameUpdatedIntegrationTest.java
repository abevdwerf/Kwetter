package com.kwetter.tweetservice;

import com.kwetter.tweetservice.dto.UserMessage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static com.kwetter.tweetservice.messaging.RabbitMQConfiguration.EXCHANGE_NAME;
import static com.kwetter.tweetservice.messaging.RabbitMQConfiguration.ROUTING_KEY;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Tag("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserDisplayNameUpdatedIntegrationTest {

    @Container
    @ServiceConnection
    static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:management");

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TweetRepository tweetRepository;

    @BeforeEach
    void setUp() {
        Tweet tweet = Tweet.builder().
                userId("1").
                displayName("john doe").
                content("This is a test tweet")
                .build();
        tweetRepository.save(tweet);
    }

    @Test
    void shouldHandleUserDisplayNameChangedEvent() {
        UserMessage userMessage =
                new UserMessage("1", "Johnny doe", "johndoe");

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, userMessage);

        await().atMost(10, SECONDS).untilAsserted(() -> {
            List<Tweet> tweetList = tweetRepository.findByUserId("1");
            assertThat(tweetList).isNotEmpty();
            for (Tweet tweet : tweetList) {
                assertThat(tweet.getDisplayName()).isEqualTo("Johnny doe");
            }
        });
    }
}

//source https://github.com/sivaprasadreddy/java-testing-made-easy/blob/main/spring-boot-rabbitmq-testcontainers-demo/src/test/java/com/sivalabs/demo/ProductPriceChangedEventHandlerTest.java
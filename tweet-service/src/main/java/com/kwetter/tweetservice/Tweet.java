package com.kwetter.tweetservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tweet")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    @Id
    private String id;
    private String userId;
    private String displayName;
    private String content;
    private int likesCount;
    private int commentCount;
    private int repostCount;
    @CreatedDate
    private LocalDateTime createdAt;
}

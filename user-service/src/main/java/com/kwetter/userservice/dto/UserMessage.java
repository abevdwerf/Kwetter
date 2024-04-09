package com.kwetter.userservice.dto;

public class UserMessage {
    private Integer id;
    private String displayName;
    private String userName;

    public UserMessage(Integer id, String displayName, String userName) {
        this.id = id;
        this.displayName = displayName;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

package com.alexeym.atmayoga.common;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey Matveev on 09.06.2018
 */
@Document(collection = "yogaUsersMetadata", schemaVersion= "1.0")
public class UserMetadata {

    @Id
    private Integer userId;
    private List<String> receivedUpdateMessages = new ArrayList<>();

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getReceivedUpdateMessages() {
        return receivedUpdateMessages;
    }

    public void setReceivedUpdateMessages(List<String> receivedUpdateMessages) {
        this.receivedUpdateMessages = receivedUpdateMessages;
    }
}

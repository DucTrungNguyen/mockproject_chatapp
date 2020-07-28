package com.rikkei.tranning.chatapp.Model;

public class Friend {
    private String friendId;
    private String type;

    public Friend() {
    }

    public Friend(String friendId, String type) {
        this.friendId = friendId;
        this.type = type;
    }
    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.rikkei.tranning.chatapp.services.models;

public class FriendModel {
    private String userImgUrl;
    private String name;
    private String friendId;
    private String type;

    public FriendModel() {
    }

    public FriendModel(String friendId, String type, String name, String userImgUrl) {
        this.userImgUrl = userImgUrl;
        this.friendId = friendId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAvatar() {
        return userImgUrl;
    }

    public void setAvatar(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }
}

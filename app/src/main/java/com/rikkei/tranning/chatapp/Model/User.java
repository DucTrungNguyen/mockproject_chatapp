package com.rikkei.tranning.chatapp.Model;

public class User {
    private String userId;
    private String userName;
    private String userEmail;
    private String userImgUrl;
    private String userPhone;
    private String userDateOfBirth;

    public User() {
    }

    public User(String userId, String userName, String userEmail, String userImgUrl, String userPhone, String userDateOfBirth) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImgUrl = userImgUrl;
        this.userPhone = userPhone;
        this.userDateOfBirth = userDateOfBirth;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserDateOfBirth() {
        return userDateOfBirth;
    }

    public void setUserDateOfBirth(String userDateOfBirth) {
        this.userDateOfBirth = userDateOfBirth;
    }
}

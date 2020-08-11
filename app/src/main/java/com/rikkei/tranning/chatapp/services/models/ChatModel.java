package com.rikkei.tranning.chatapp.services.models;

import java.util.ArrayList;

public class ChatModel {
    private UserModel userModel;
    private ArrayList<MessageModel> messageModelArrayList;

    public ChatModel() {
    }

    public ChatModel(UserModel userModel, ArrayList<MessageModel> messageModelArrayList) {
        this.userModel = userModel;
        this.messageModelArrayList = messageModelArrayList;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ArrayList<MessageModel> getMessageModelArrayList() {
        return messageModelArrayList;
    }

    public void setMessageModelArrayList(ArrayList<MessageModel> messageModelArrayList) {
        this.messageModelArrayList = messageModelArrayList;
    }
}

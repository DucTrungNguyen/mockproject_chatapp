package com.rikkei.tranning.chatapp.services.models;

import java.util.Date;

public class MessageModel {
    private String idSender;
    private String idReceiver;
    private String message;
    private String type;
    private String date;
    private String time;

    public MessageModel(String idSender, String idReceiver, String message, String type, String date, String time) {
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.message = message;
        this.type = type;
        this.date = date;
        this.time=time;
    }

    public MessageModel() {
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

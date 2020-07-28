package com.rikkei.tranning.chatapp.views.uis.login;

public interface LoginNavigator {
    void replaceFragment();
    void login();
    void setEnableButton();
    void showMessageLogin(String message);
    void moveIntent();
}

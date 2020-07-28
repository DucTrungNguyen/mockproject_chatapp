package com.rikkei.tranning.chatapp.Navigator;

public interface LoginNavigator {
    void replaceFragment();
    void login();
    void setEnableButton();
    void showMessageLogin(String message);
    void moveIntent();
}

package com.rikkei.tranning.chatapp.Navigator;

public interface SignUpNavigator {
    void replaceFragment();
    void signup();
    void setEnableButton();
    void requireName(String message);
    void showMessageSignUp(String message);
}

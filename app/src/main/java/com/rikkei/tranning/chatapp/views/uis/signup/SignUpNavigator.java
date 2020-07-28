package com.rikkei.tranning.chatapp.views.uis.signup;

public interface SignUpNavigator {
    void replaceFragment();
    void signup();
    void setEnableButton();
    void requireName(String message);
    void showMessageSignUp(String message);
}

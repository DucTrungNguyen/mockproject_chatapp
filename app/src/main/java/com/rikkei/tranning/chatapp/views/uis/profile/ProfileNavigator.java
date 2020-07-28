package com.rikkei.tranning.chatapp.views.uis.profile;

public interface ProfileNavigator {
    void showMessage(String message);
    void replaceFragment();
    void removeFragment();
    void logout();
    void openImage();
    void updateInfoUser();
}

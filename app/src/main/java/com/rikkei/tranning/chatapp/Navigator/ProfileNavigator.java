package com.rikkei.tranning.chatapp.Navigator;

public interface ProfileNavigator {
    void showMessage(String message);
    void replaceFragment();
    void removeFragment();
    void logout();
    void openImage();
    void updateInfoUser();
}

package com.rikkei.tranning.chatapp.views.uis.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.rikkei.tranning.chatapp.Base.BaseViewModel;

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> {
    public void replaceFragmentClick(){
        getNavigator().replaceFragment();
    }
    public void logoutClick(){
        FirebaseAuth.getInstance().signOut();
        getNavigator().logout();
    }
}

package com.rikkei.tranning.chatapp.views.uis.profile;

import com.rikkei.tranning.chatapp.Base.BaseViewModel;

public class EditProfileViewModel  extends BaseViewModel<EditProfileNavigator> {
    public void removeFragmentClick(){
        getNavigator().removeFragment();
    }
    public void setImageOnClick(){
        getNavigator().openImage();
    }
    public void saveClick(){
        getNavigator().updateInfoUser();
        getNavigator().removeFragment();
    }
}
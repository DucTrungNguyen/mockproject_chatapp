package com.rikkei.tranning.chatapp.views.uis.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.ProfileRepositories;

public class EditProfileViewModel  extends ViewModel {
    public MutableLiveData<UserModel> userMutableLiveData=new MutableLiveData<>();
    public void getInfoUser(){
        new ProfileRepositories().infoUserFromFirebase(new ProfileRepositories.DataStatus() {
            @Override
            public void DataIsLoaded(UserModel user) {
                userMutableLiveData.setValue(user);
            }
        });
    }
    public void updateInfoUser(String key, String value){
        new ProfileRepositories().updateInforFromFirebase(key,value);
    }
}
package com.rikkei.tranning.chatapp.views.uis.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.services.network.Network;
import com.rikkei.tranning.chatapp.services.repositories.ProfileRepositories;
public class ProfileViewModel extends ViewModel {
    public MutableLiveData<User> userMutableLiveData =new MutableLiveData<>();
    public void getInfoUser(){
        new ProfileRepositories().infoUserFromFirebase(new ProfileRepositories.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                userMutableLiveData.setValue(user);
            }
        });
    }
}

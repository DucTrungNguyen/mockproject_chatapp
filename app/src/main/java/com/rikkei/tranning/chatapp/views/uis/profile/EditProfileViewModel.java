package com.rikkei.tranning.chatapp.views.uis.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.ProfileRepository;

import java.util.ArrayList;

public class EditProfileViewModel  extends ViewModel {
    public MutableLiveData<UserModel> userMutableLiveData=new MutableLiveData<>();
    public void getInfoUser(){
        new ProfileRepository().infoUserFromFirebase(user -> userMutableLiveData.setValue(user));
    }
    public void updateInfoUser(String key, String value){
        new ProfileRepository().updateInforFromFirebase(key,value);
    }
    public MutableLiveData<ArrayList<UserModel>> userArrayLiveData=new MutableLiveData<>();
//    public void updateArray(String s){
//        new AllFriendRepository().searchUser(s, new AllFriendRepository.DataStatus() {
//            @Override
//            public void DataIsLoaded(ArrayList<UserModel> userArrayList) {
//                userArrayLiveData.setValue(userArrayList);
//            }
//        });
//    }
}
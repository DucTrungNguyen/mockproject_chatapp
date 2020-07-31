package com.rikkei.tranning.chatapp.views.uis.friend.allfriends;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.services.repositories.AllFriendRepositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AllFriendViewModel extends ViewModel {
    public static MutableLiveData<ArrayList<User>> listUserMutableLiveData=new MutableLiveData<>();
    public void getAllUserInfo(){
        new AllFriendRepositories().getAllUser(new AllFriendRepositories.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<User> userArrayList) {
                listUserMutableLiveData.setValue(userArrayList);
            }
        });
    }
    public void collectionArray(ArrayList<User> userArray){
        Collections.sort(userArray, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        });
    }
}

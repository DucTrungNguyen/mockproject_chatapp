package com.rikkei.tranning.chatapp.views.uis.friend;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.services.repositories.AllFriendRepositories;
import com.rikkei.tranning.chatapp.services.repositories.MyFriendRepositories;
import java.util.ArrayList;

public class FriendViewModel extends ViewModel {
    MutableLiveData<ArrayList<User>> userArrayLiveData=new MutableLiveData<>();
    MutableLiveData<ArrayList<User>> myFriendSearchLiveData=new MutableLiveData<>();
    public void updateArrayMyFriend(String s){
        new MyFriendRepositories().searchFriend(s, new MyFriendRepositories.DataStatus() {
            @Override
            public void DataIsLoading(ArrayList<User> arrayList) {
                myFriendSearchLiveData.setValue(arrayList);
            }
        });
    }
    public void updateArray(String s){
        new AllFriendRepositories().searchUser(s, new AllFriendRepositories.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<User> userArrayList) {
                userArrayLiveData.setValue(userArrayList);
            }
        });
    }
}

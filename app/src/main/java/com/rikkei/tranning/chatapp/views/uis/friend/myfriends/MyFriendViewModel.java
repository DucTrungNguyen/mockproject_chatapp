package com.rikkei.tranning.chatapp.views.uis.friend.myfriends;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.services.repositories.MyFriendRepositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyFriendViewModel extends ViewModel {
    public static  MutableLiveData<ArrayList<User>> listMyFriendMutableLiveData=new MutableLiveData<>();
    public void getFriendArray(){
        new MyFriendRepositories().getAllFriend(new MyFriendRepositories.DataStatus() {
            @Override
            public void DataIsLoading(ArrayList<User> arrayList) {
                listMyFriendMutableLiveData.setValue(arrayList);
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

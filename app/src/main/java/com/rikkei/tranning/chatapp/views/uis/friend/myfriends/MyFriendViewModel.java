package com.rikkei.tranning.chatapp.views.uis.friend.myfriends;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.MyFriendRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyFriendViewModel extends ViewModel {
    public static  MutableLiveData<ArrayList<UserModel>> listMyFriendMutableLiveData=new MutableLiveData<>();
    public void getFriendArray(){
        new MyFriendRepository().getAllFriend(new MyFriendRepository.DataStatus() {
            @Override
            public void DataIsLoading(ArrayList<UserModel> arrayList) {
                listMyFriendMutableLiveData.setValue(arrayList);
            }
        });
    }
    public void collectionArray(ArrayList<UserModel> userArray){
        Collections.sort(userArray, new Comparator<UserModel>() {
            @Override
            public int compare(UserModel o1, UserModel o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        });
    }
}

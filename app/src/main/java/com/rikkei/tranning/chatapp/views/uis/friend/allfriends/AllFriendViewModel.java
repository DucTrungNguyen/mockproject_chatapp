package com.rikkei.tranning.chatapp.views.uis.friend.allfriends;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.AllFriendRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AllFriendViewModel extends ViewModel {
    public static MutableLiveData<ArrayList<UserModel>> listUserMutableLiveData=new MutableLiveData<>();
    public void getAllUserInfo(){
        new AllFriendRepository().getAllUser(new AllFriendRepository.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<UserModel> userArrayList) {
                listUserMutableLiveData.setValue(userArrayList);
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

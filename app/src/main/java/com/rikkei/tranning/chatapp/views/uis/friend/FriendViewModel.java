package com.rikkei.tranning.chatapp.views.uis.friend;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.AllFriendRepository;
import com.rikkei.tranning.chatapp.services.repositories.MyFriendRepository;

import java.util.ArrayList;

public class FriendViewModel extends ViewModel {
    MutableLiveData<ArrayList<UserModel>> userArrayLiveData=new MutableLiveData<>();
    MutableLiveData<ArrayList<UserModel>> myFriendSearchLiveData=new MutableLiveData<>();
    MutableLiveData<ArrayList<UserModel>> requestFriendSearchLiveData=new MutableLiveData<>();
    public void updateArrayMyFriend(String s){
        new MyFriendRepository().searchFriend(s, new MyFriendRepository.DataStatus() {
            @Override
            public void DataIsLoading(ArrayList<UserModel> arrayList) {
                myFriendSearchLiveData.setValue(arrayList);
            }
        });
    }

    public void updateArrayRequestFriend(String s){
//        new RequestFriendRepository().searchFriend(s, new RequestFriendRepository.DataStatus() {
//            @Override
//            public void DataIsLoading(ArrayList<UserModel> arrayList) {
//                requestFriendSearchLiveData.setValue(arrayList);
//            }
//        });
    }

    public void updateArray(String s){
        new AllFriendRepository().searchUser(s, new AllFriendRepository.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<UserModel> userArrayList) {
                userArrayLiveData.setValue(userArrayList);
            }
        });
    }
}

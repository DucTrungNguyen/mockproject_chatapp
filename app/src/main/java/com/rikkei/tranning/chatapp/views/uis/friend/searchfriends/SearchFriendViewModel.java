package com.rikkei.tranning.chatapp.views.uis.friend.searchfriends;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.MyFriendRepository;
import com.rikkei.tranning.chatapp.views.uis.friend.myfriends.MyFriendViewModel;

import java.util.ArrayList;

public class SearchFriendViewModel extends ViewModel {
    MutableLiveData<ArrayList<UserModel>> listFriendSearchLiveData=new MutableLiveData<>();
    public void getListFriendSearch(String s){
        new MyFriendRepository().searchFriend(s, new MyFriendRepository.DataStatus() {
            @Override
            public void DataIsLoading(ArrayList<UserModel> arrayList) {
                listFriendSearchLiveData.setValue(arrayList);
            }
        });
    }
}

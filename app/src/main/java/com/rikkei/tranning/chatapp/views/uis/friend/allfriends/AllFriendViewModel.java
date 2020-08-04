package com.rikkei.tranning.chatapp.views.uis.friend.allfriends;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;

import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.AllFriendRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AllFriendViewModel extends ViewModel {
    AllFriendRepository allFriendRepository;

    public AllFriendViewModel() {
        allFriendRepository=new AllFriendRepository();
    }

    public static MutableLiveData<ArrayList<AllUserModel>> listUserMutableLiveData=new MutableLiveData<>();

    public void getAllUserInfo(){
       allFriendRepository.getAllUser(new AllFriendRepository.Data() {
           @Override
           public void typeFriendIsLoad(ArrayList<AllUserModel> allUserModels) {
               listUserMutableLiveData.setValue(allUserModels);
           }
       });
    }
    public void collectionArray(ArrayList<AllUserModel> userArray){
        Collections.sort(userArray, new Comparator<AllUserModel>() {
            @Override
            public int compare(AllUserModel o1, AllUserModel o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        });
    }
    public void createFriend(AllUserModel userModel){
        allFriendRepository.createFriend(userModel);
    }
    public void deleteFriend(AllUserModel userModel){
        allFriendRepository.deleteFriend(userModel);
    }
    public void updateFriend(AllUserModel userModel){
        allFriendRepository.updateFriend(userModel);
    }
}

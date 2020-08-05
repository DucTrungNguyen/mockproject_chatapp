package com.rikkei.tranning.chatapp.views.uis.friend;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.services.repositories.FriendsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class SharedFriendViewModel extends ViewModel {
    public ArrayList<AllUserModel> arrayAllFriend=new ArrayList<>();
    FriendsRepository friendRepository;
    public MutableLiveData<ArrayList<AllUserModel>> allUserListLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<AllUserModel>> getUserFromLiveData=new MutableLiveData<>();
    public SharedFriendViewModel() {
        friendRepository = new FriendsRepository();
        friendRepository.getUserInfo(new FriendsRepository.InfoUser() {
            @Override
            public void InfoUserLoaded(ArrayList<AllUserModel> allUserModels) {
                allUserListLiveData.setValue(allUserModels);
                getUserFromLiveData.setValue(allUserModels);
                arrayAllFriend=allUserModels;
            }
        });
    }
    public void collectionArray(ArrayList<AllUserModel> userArray) {
        Collections.sort(userArray, new Comparator<AllUserModel>() {
            @Override
            public int compare(AllUserModel o1, AllUserModel o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        });
    }

    public void createFriend(AllUserModel userModel) {
        friendRepository.createFriend(userModel);
    }

    public void deleteFriend(AllUserModel userModel) {
        friendRepository.deleteFriend(userModel);
    }

    public void updateFriend(AllUserModel userModel) {
        friendRepository.updateFriend(userModel);
    }

    public void searchFriend(final String s, ArrayList<AllUserModel> getUserFromLiveData) {
        ArrayList<AllUserModel> allUserList = new ArrayList<>();
            for (int i = 0; i < getUserFromLiveData.size(); i++) {
                String a = getUserFromLiveData.get(i).getUserName();
                if (a.indexOf(s) != -1) {
                    allUserList.add(getUserFromLiveData.get(i));
                }
            }
            allUserListLiveData.setValue(allUserList);
    }
}

package com.rikkei.tranning.chatapp.views.uis.friend;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.services.repositories.FriendsRepository;

import java.util.ArrayList;
import java.util.Collections;

public class SharedFriendViewModel extends ViewModel {
    public ArrayList<AllUserModel> arrayAllFriend = new ArrayList<>();
    FriendsRepository friendRepository;
    public MutableLiveData<ArrayList<AllUserModel>> allUserListLiveData = new MutableLiveData<>();
    public MutableLiveData<ArrayList<AllUserModel>> getUserFromLiveData = new MutableLiveData<>();
    public MutableLiveData<String> countNotifiRequest = new MutableLiveData<>("0");
    Integer count =0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SharedFriendViewModel() {
        friendRepository = new FriendsRepository();
        friendRepository.getUserInfo((allUserModels, count) -> {

            allUserListLiveData.setValue(allUserModels);
            getUserFromLiveData.setValue(allUserModels);
            arrayAllFriend = allUserModels;


//            allUserModels.forEach(user ->{
//                if(user.getUserType().equals("friendRequest")){
//                    count++;
//                }
//            });

            if ( count > 10){
                countNotifiRequest.setValue("9+");
            }
            else
                countNotifiRequest.setValue(Integer.toString(count));
            count = 0;

        });
    }

    public void collectionArray(ArrayList<AllUserModel> userArray) {
        Collections.sort(userArray, (o1, o2) -> o1.getUserName().substring(0,1).compareTo(o2.getUserName().substring(0,1)));
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
            if (a.contains(s)) {
                allUserList.add(getUserFromLiveData.get(i));
            }
        }
        allUserListLiveData.setValue(allUserList);
    }
}

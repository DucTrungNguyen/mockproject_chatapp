package com.rikkei.tranning.chatapp.views.uis.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.services.models.editUser;
import com.rikkei.tranning.chatapp.services.repositories.ProfileRepositories;

public class EditProfileViewModel  extends ViewModel {
    public MutableLiveData<User> userMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<String> userName=new MutableLiveData<>();
    public MutableLiveData<String> userPhone=new MutableLiveData<>();
    public MutableLiveData<String> userDateOfBirth=new MutableLiveData<>();
    public MutableLiveData<editUser> editUserMutableLiveData=new MutableLiveData<>();
    public void getInfoUser(){
        new ProfileRepositories().infoUserFromFirebase(new ProfileRepositories.DataStatus() {
            @Override
            public void DataIsLoaded(User user) {
                userMutableLiveData.setValue(user);
            }
        });
    }
    public void saveButtonOnClick(){
        editUser user=new editUser(userName.getValue(),userPhone.getValue(),userDateOfBirth.getValue());
        editUserMutableLiveData.setValue(user);
    }
    public void updateInfoUser(String key, String value){
        new ProfileRepositories().updateInforFromFirebase(key,value);
    }
}
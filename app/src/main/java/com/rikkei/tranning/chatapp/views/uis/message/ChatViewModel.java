package com.rikkei.tranning.chatapp.views.uis.message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.MessageModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.ChatRepository;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    MutableLiveData<UserModel> userChatLiveData =new MutableLiveData<>();
    MutableLiveData<ArrayList<MessageModel>> messageListLiveData=new MutableLiveData<>();
    public  void getInfoUserChat(String id){
        new ChatRepository().infoUserFromFirebase(id, new ChatRepository.DataStatus() {
            @Override
            public void DataIsLoaded(UserModel user) {
                userChatLiveData.setValue(user);
            }
        });
    }
    public void sendMessage(String idUser, String message){
        new ChatRepository().createMessage(idUser,message);
    }
    public void displayMessage(String idFriend){
        new ChatRepository().getMessage(idFriend, new ChatRepository.MessageStatus() {
            @Override
            public void DataIsLoaded(ArrayList<MessageModel> messageArray) {
                messageListLiveData.setValue(messageArray);
            }
        });

    }
}

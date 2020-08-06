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
    ChatRepository chatRepository;
    public ChatViewModel() {
        chatRepository=new ChatRepository();
    }

    public  void getInfoUserChat(String id){
        chatRepository.infoUserFromFirebase(id, user -> userChatLiveData.setValue(user));
    }
    public void sendMessage(String idUser, String message){
        chatRepository.createMessage(idUser,message);
    }
    public void displayMessage(String idFriend){
        chatRepository.getMessage(idFriend, messageArray -> messageListLiveData.setValue(messageArray));

    }
}

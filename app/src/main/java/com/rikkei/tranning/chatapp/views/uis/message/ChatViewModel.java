package com.rikkei.tranning.chatapp.views.uis.message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rikkei.tranning.chatapp.services.models.MessageModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.ChatRepository;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    MutableLiveData<UserModel> userChatLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<MessageModel>> messageListLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<UserModel>> arrayInfoUserChatLiveData=new MutableLiveData<>();

    ChatRepository chatRepository;

    public ChatViewModel() {
        chatRepository = new ChatRepository();
        chatRepository.getAllInfoUserChat(arrayInfoAllUserChat -> arrayInfoUserChatLiveData.setValue(arrayInfoAllUserChat));
    }

    public void getInfoUserChat(String id) {
        chatRepository.infoUserFromFirebase(id, user -> userChatLiveData.setValue(user));
    }

    public void sendMessage(String idUser, String message, String type) {
        chatRepository.createMessage(idUser, message,type);
    }
    public void displayMessage(String idFriend) {
        chatRepository.getSomeOfMessage(idFriend, messageArray -> messageListLiveData.setValue(messageArray));
    }
    public void getListMessage(String id, LastMessage lastMessage){
        chatRepository.getMessage(id, messageArray -> {
            for (int i=0;i<messageArray.size();i++){
                String message= messageArray.get(i).getMessage();
                String time= messageArray.get(i).getTime();
                String date= messageArray.get(i).getDate();
                String type=messageArray.get(i).getType();
                lastMessage.isLoad(message,time,date,type);
            }
        });
    }
    public void checkSeen(String id){
        chatRepository.checkSeen(id);
    }
    public interface LastMessage{
        void isLoad(String message, String time, String date,String type);
    }
}

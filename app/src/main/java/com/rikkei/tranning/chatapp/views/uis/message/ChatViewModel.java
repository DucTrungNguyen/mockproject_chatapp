package com.rikkei.tranning.chatapp.views.uis.message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.rikkei.tranning.chatapp.services.models.ChatModel;
import com.rikkei.tranning.chatapp.services.models.MessageModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.repositories.ChatRepository;

import java.util.ArrayList;
import java.util.Collections;

public class ChatViewModel extends ViewModel {
    MutableLiveData<UserModel> userChatLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<MessageModel>> messageListLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<ChatModel>> arrayInfoUserChatLiveData = new MutableLiveData<>();
    MutableLiveData<ArrayList<ChatModel>> arraySearchLiveData = new MutableLiveData<>();
    public MutableLiveData<String> countUnReadMessage = new MutableLiveData<>();
    ChatRepository chatRepository;

    public ChatViewModel() {
        chatRepository = new ChatRepository();
        chatRepository.getListMessage(listChatArray -> {
            arrayInfoUserChatLiveData.setValue(listChatArray);
            arraySearchLiveData.setValue(listChatArray);
        });
    }

    public void getInfoUserChat(String id) {
        chatRepository.infoUserFromFirebase(id, user -> userChatLiveData.setValue(user));
    }

    public void sendMessage(String idUser, String message, String type) {
        chatRepository.createMessage(idUser, message, type);
    }

    public void displayMessage(String idFriend) {
        chatRepository.getMessage(idFriend, messageArray -> messageListLiveData.setValue(messageArray));
    }

    public DatabaseReference checkSeen(String id) {
        return chatRepository.checkSeen(id);
    }

    public void searchUserChat(final String s, ArrayList<ChatModel> getUserFromLiveData) {
        ArrayList<ChatModel> allUserList = new ArrayList<>();
        for (int i = 0; i < getUserFromLiveData.size(); i++) {
            String a = getUserFromLiveData.get(i).getUserModel().getUserName();
            if (a.toLowerCase().contains(s.toLowerCase())) {
                allUserList.add(getUserFromLiveData.get(i));
            }
        }
        arrayInfoUserChatLiveData.setValue(allUserList);
    }

    public void softArray(ArrayList<ChatModel> chatModelArrayList) {
        int size = chatModelArrayList.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                int size1 = chatModelArrayList.get(j).getMessageModelArrayList().size() - 1;
                int size2 = chatModelArrayList.get(j + 1).getMessageModelArrayList().size() - 1;
                if (chatModelArrayList.get(j).getMessageModelArrayList().get(size1).getTimeLong() <
                        chatModelArrayList.get(j + 1).getMessageModelArrayList().get(size2).getTimeLong()) {
                    Collections.swap(chatModelArrayList, j, j + 1);
                }
            }
        }
    }
    public int getCountUnReadMessage(ArrayList<ChatModel> chatModelArrayList){
        int count=0;
        int size = chatModelArrayList.size();
        for (int i=0;i< size;i++){
            int size2=chatModelArrayList.get(i).getMessageModelArrayList().size();
            if (!chatModelArrayList.get(i).getMessageModelArrayList().get(size2-1).getCheckSeen()){
                count++;
            }
        }
        return count;
    }
}

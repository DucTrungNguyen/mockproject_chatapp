package com.rikkei.tranning.chatapp.views.uis.message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MessageViewModel extends ViewModel {

    public MutableLiveData<ArrayList<MessageViewModel>>
            listMessageFiendViewModelLiveData=new MutableLiveData<>();

}

package com.rikkei.tranning.chatapp.views.uis.friend.requestfriends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rikkei.tranning.chatapp.services.models.UserModel
import com.rikkei.tranning.chatapp.services.repositories.MyFriendRepository
import com.rikkei.tranning.chatapp.services.repositories.RequestFriendRepository
import com.rikkei.tranning.chatapp.views.uis.friend.myfriends.MyFriendViewModel
import java.util.*

class RequestFriendViewModel :ViewModel() {
    var listRequestFriendMutableLiveData = MutableLiveData<ArrayList<UserModel>>()
    var listSendFriendMutableLiveData = MutableLiveData<ArrayList<UserModel>>()

    fun getRequestFriendArray() {
        RequestFriendRepository().getRequestFriend { arrayList ->
            listRequestFriendMutableLiveData.value = arrayList


        }
    }

    fun getSendFriendArray() {
        RequestFriendRepository().getSendFriend { arrayList ->
            listSendFriendMutableLiveData.value = arrayList
        }
    }
    fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }

    fun collectionArray(userArray: ArrayList<UserModel>) {
        Collections.sort(userArray,
            Comparator<UserModel?> { o1, o2 -> o1!!.userName.compareTo(o2!!.userName) })
    }
}
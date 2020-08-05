package com.rikkei.tranning.chatapp.views.uis.friend.requestfriends

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rikkei.tranning.chatapp.BuildConfig
import com.rikkei.tranning.chatapp.services.models.FriendsModel
import com.rikkei.tranning.chatapp.services.models.UserModel
import com.rikkei.tranning.chatapp.services.repositories.RequestFriendRepository
import java.util.*

class RequestFriendViewModel :ViewModel() {

    var listRequestFriendMutableLiveData = MutableLiveData<ArrayList<UserModel>>()
    var listSendFriendMutableLiveData = MutableLiveData<ArrayList<UserModel>>()
    var requestFriendRepository = RequestFriendRepository.instance

    fun getRequestFriendArray() {
        var requestUserArrayList = ArrayList<UserModel>()
        requestFriendRepository.getRequestFriend()?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                requestUserArrayList.clear();
//                sendUserArrayList.clear()
                for (Node in dataSnapshot.children) {
                    val friends: FriendsModel? = Node.getValue(FriendsModel::class.java)
                    if (BuildConfig.DEBUG && friends == null) {
                        error("Assertion failed")
                    }
                    if (friends!!.type == "friendRequest") {
                        requestFriendRepository.getDocUser(friends.friendId)!!
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val user: UserModel = dataSnapshot.getValue(UserModel::class.java)!!
                                    requestUserArrayList.add(user)

                                }

                                override fun onCancelled(databaseError: DatabaseError) {}
                            })

                    }
                    listSendFriendMutableLiveData.value = requestUserArrayList
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        }

    fun getSendFriendArray() {

        var sendUserArrayList = ArrayList<UserModel>()
        requestFriendRepository.getRequestFriend()?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (Node in dataSnapshot.children) {
                    val friends: FriendsModel? = Node.getValue(FriendsModel::class.java)
                    if (BuildConfig.DEBUG && friends == null) {
                        error("Assertion failed")
                    }
                    if (friends!!.type == "sendRequest") {
                        requestFriendRepository.getDocUser(friends.friendId)!!
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    val user: UserModel = dataSnapshot.getValue(UserModel::class.java)!!
                                    sendUserArrayList.add(user)

                                }
                                override fun onCancelled(databaseError: DatabaseError) {}
                            })

                    }
                    listSendFriendMutableLiveData.value = sendUserArrayList
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
//        listSendFriendMutableLiveData.value = sendUserArrayList
    }


    fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }

    fun collectionArray(userArray: ArrayList<UserModel>) {
        Collections.sort(userArray,
            Comparator<UserModel?> { o1, o2 -> o1!!.userName.compareTo(o2!!.userName) })
    }
}
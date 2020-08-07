package com.rikkei.tranning.chatapp.services.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.rikkei.tranning.chatapp.services.models.FriendsModel
import com.rikkei.tranning.chatapp.services.models.UserModel
import java.util.*

class RequestFriendRepository private constructor() {
    var friendType: String? = null
    private val friendArrayList = ArrayList<FriendsModel>()

    private var databaseReference: DatabaseReference? = null
    private var fireUser = FirebaseAuth.getInstance().currentUser


    var friends: FriendsModel? = FriendsModel()
    var user = UserModel()
    var userArrayList = ArrayList<UserModel>()
    var requestUserArrayList = ArrayList<UserModel>()
    var sendUserArrayList = ArrayList<UserModel>()
    var friendsArrayList = ArrayList<FriendsModel>()

    companion object {
        val instance = RequestFriendRepository()
    }

    init {
        databaseReference = FirebaseDatabase.getInstance().reference
    }

    interface DataStatus {
        fun dataIsLoading(arrayList: ArrayList<UserModel>?)
    }

    interface TypeFriend {
        fun typeFriendIsLoad(s: String?)
    }

    fun getRequestFriend(): DatabaseReference? {
        val userId = fireUser!!.uid
        return databaseReference?.child("friend")?.child(userId)

    }

    fun getDocUser(friendId: String): DatabaseReference? {

        return databaseReference?.child("user")?.child(friendId)

    }


    fun searchFriend(
        s: String,
        dataStatus: DataStatus
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val query =
            FirebaseDatabase.getInstance().getReference("friend").child(firebaseUser!!.uid)
                .orderByChild("friendId")
                .startAt(s)
                .endAt(s + "\uf8ff")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val friends =
                        snapshot.getValue(FriendsModel::class.java)!!
                    databaseReference = FirebaseDatabase.getInstance().reference.child("user")
                        .child(friends.friendId)
                    databaseReference!!.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val userArrayList =
                                ArrayList<UserModel>()
                            user = dataSnapshot.getValue(UserModel::class.java)!!
                            userArrayList.add(user)
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }
                dataStatus.dataIsLoading(userArrayList)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun searchFriendType(
        user: UserModel,
        type: TypeFriend
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("friend").child(userId)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                friendArrayList.clear()
                for (keyNode in dataSnapshot.children) {
                    val account =
                        keyNode.getValue(FriendsModel::class.java)!!
                    if (account.friendId == user.userId) {
                        friendArrayList.add(account)
                        when (account.type) {
                            "friend" -> {
                                friendType = "friend"
                            }
                            "sendRequest" -> {
                                friendType = "sendRequest"
                            }
                            "friendRequest" -> {
                                friendType = "friendRequest"
                            }
                        }
                    }
                }
                if (friendArrayList.size == 0) {
                    friendType = "NoFriend"
                }
                type.typeFriendIsLoad(friendType)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun createFriend(user: UserModel) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend")
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser!!.uid
        val friend = FriendsModel(user.userId, "sendRequest")
        val friend2 = FriendsModel(userId, "friendRequest")
        databaseReference!!.child(userId).child(user.userId).setValue(friend)
        databaseReference!!.child(user.userId).child(userId).setValue(friend2)
    }

    fun deleteFriend(user: UserModel) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend")
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser!!.uid
        databaseReference!!.child(userId).child(user.userId).setValue(null)
        databaseReference!!.child(user.userId).child(userId).setValue(null)
    }

    fun updateFriend(user: UserModel) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend")
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userId = firebaseUser!!.uid
        databaseReference!!.child(userId).child(user.userId).child("type").setValue("friend")
        databaseReference!!.child(user.userId).child(userId).child("type").setValue("friend")
    }
}
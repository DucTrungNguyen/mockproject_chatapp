package com.rikkei.tranning.chatapp.services.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.rikkei.tranning.chatapp.services.models.MessageModel
import java.util.*

class MessageRepository{

    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var fireAuth: FirebaseUser
    var currentUserId: String = FirebaseAuth.getInstance().currentUser!!.uid

    companion object {
        val instance = MessageRepository()
    }

    interface MessageStatus {
        fun DataIsLoaded(messageArray: ArrayList<MessageModel?>?)
    }


    fun getMessageFriend( messageStatus : MessageRepository.MessageStatus){
        val list  = firebaseDatabase.getReference("chat")


    }



}
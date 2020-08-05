package com.rikkei.tranning.chatapp.services.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.rikkei.tranning.chatapp.services.models.MessageModel
import java.util.*

class MessageRepository{

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var fireAuth : FirebaseUser
    lateinit var currentUserId : String
    companion object{
        val instance  = MessageRepository()
    }
    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        currentUserId = FirebaseAuth.getInstance().currentUser!!.uid;


    }

    interface MessageStatus {
        fun DataIsLoaded(messageArray: ArrayList<MessageModel?>?)
    }


    fun getMessageFriend( messageStatus : MessageRepository.MessageStatus){
        val list  = firebaseDatabase.getReference("chat")

    }



}
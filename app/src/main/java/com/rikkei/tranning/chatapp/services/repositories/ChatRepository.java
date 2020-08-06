package com.rikkei.tranning.chatapp.services.repositories;

import android.text.format.Time;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.tranning.chatapp.services.models.MessageModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatRepository {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    ArrayList<MessageModel> messageList=new ArrayList<>();
    FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    ArrayList<String> listKeyUserChat=new ArrayList<>();
    ArrayList<UserModel> listUserChat=new ArrayList<>();
    String keyId;
    public interface DataStatus{
        void DataIsLoaded(UserModel user);
    }
    public interface MessageStatus{
        void DataIsLoaded(ArrayList<MessageModel> messageArray);
    }
    public void infoUserFromFirebase(String id, final DataStatus dataStatus){
        databaseReference= FirebaseDatabase.getInstance().getReference("user").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel account=dataSnapshot.getValue(UserModel.class);
                dataStatus.DataIsLoaded(account);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void createMessage(String id, String message){
        final Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormatDate=new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat simpleDateFormatTime=new SimpleDateFormat("hh:mm");
        String hour=simpleDateFormatTime.format(calendar.getTime());
        String date=simpleDateFormatDate.format(calendar.getTime());
        databaseReference=FirebaseDatabase.getInstance().getReference("chat");
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String idUser=firebaseUser.getUid();
        MessageModel messageModel =new MessageModel(idUser,id,message, "Text",date,hour);
        String key;
        if(id.compareTo(idUser)>0){
            key=id+idUser;
        }
        else{
            key=idUser+id;
        }
        databaseReference.child(key).push().setValue(messageModel);
    }
    public void getMessage(String idFriend, final MessageStatus messageStatus){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String myId=firebaseUser.getUid();
        String key;
        if(idFriend.compareTo(myId)>0){
            key=idFriend+myId;
        }
        else{
           key=myId+idFriend;
        }
        databaseReference=FirebaseDatabase.getInstance().getReference("chat").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear();
                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                    MessageModel message=keyNode.getValue(MessageModel.class);
                    messageList.add(message);
                }
                messageStatus.DataIsLoaded(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void getListUserChat(){
        databaseReference=FirebaseDatabase.getInstance().getReference("chat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listKeyUserChat.clear();
                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                   keyId=keyNode.getKey();
                   if(keyId.contains(firebaseUser.getUid())){
                       String  a = keyId.substring(0,keyId.length()/2);
                       String b = keyId.substring(keyId.length()/2,keyId.length());
                       if(a.equals(firebaseUser.getUid())){
                           listKeyUserChat.add(a);
                       }
                       else{
                           listKeyUserChat.add(b);
                       }
                   }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getInfoListUserChat(){
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }
}

package com.rikkei.tranning.chatapp.services.repositories;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.tranning.chatapp.services.models.FriendsModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;

import java.util.ArrayList;

public class AllFriendRepository {
    DatabaseReference databaseReference;
    private ArrayList<UserModel> userList = new ArrayList<>();
    String friendType;
    private ArrayList<FriendsModel> friendArrayList = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(ArrayList<UserModel> userArrayList);
    }

    public interface typeFriend {
        void typeFriendIsLoad(String s);
    }

    public void getAllUser(final DataStatus dataStatus) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    UserModel account = keyNode.getValue(UserModel.class);
                    if (!account.getUserId().equals(userId)) {
                        userList.add(account);
                    }
                }
                dataStatus.DataIsLoaded(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void searchUser(String s, final DataStatus dataStatus) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("user").orderByChild("userName")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    if (!user.getUserId().equals(firebaseUser.getUid())) {
                        userList.add(user);
                    }
                }
                dataStatus.DataIsLoaded(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createFriend(UserModel user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        FriendsModel friend = new FriendsModel(user.getUserId(), "sendRequest");
        FriendsModel friend2 = new FriendsModel(userId, "friendRequest");
        databaseReference.child(userId).child(user.getUserId()).setValue(friend);
        databaseReference.child(user.getUserId()).child(userId).setValue(friend2);
    }

    public void deleteFriend(UserModel user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference.child(userId).child(user.getUserId()).setValue(null);
        databaseReference.child(user.getUserId()).child(userId).setValue(null);
    }

    public void updateFriend(UserModel user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference.child(userId).child(user.getUserId()).child("type").setValue("friend");
        databaseReference.child(user.getUserId()).child(userId).child("type").setValue("friend");
    }
    public void searchFriendType(final UserModel user, final typeFriend type) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("friend").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendArrayList.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    FriendsModel account = keyNode.getValue(FriendsModel.class);
                    if (account.getFriendId().equals(user.getUserId())) {
                        friendArrayList.add(account);
                        if(account.getType().equals("friend")){
                            friendType="friend";
                        }
                        else if (account.getType().equals("sendRequest")) {
                            friendType = "sendRequest";
                        } else if (account.getType().equals("friendRequest")) {
                            friendType = "friendRequest";
                        }
                    }
                }
                if (friendArrayList.size() == 0) {
                    friendType = "NoFriend";
                }
                type.typeFriendIsLoad(friendType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

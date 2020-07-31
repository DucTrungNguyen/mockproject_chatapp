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
import com.rikkei.tranning.chatapp.services.models.Friend;
import com.rikkei.tranning.chatapp.services.models.Friends;
import com.rikkei.tranning.chatapp.services.models.User;

import java.util.ArrayList;

public class AllFriendRepositories {
    DatabaseReference databaseReference;
    private ArrayList<User> userList = new ArrayList<>();
    String friendType;
    private ArrayList<Friends> friendArrayList = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(ArrayList<User> userArrayList);
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
                    User account = keyNode.getValue(User.class);
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
                    User user = snapshot.getValue(User.class);
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

    public void createFriend(User user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        Friends friend = new Friends(user.getUserId(), "sendRequest");
        Friends friend2 = new Friends(userId, "friendRequest");
        databaseReference.child(userId).child(user.getUserId()).setValue(friend);
        databaseReference.child(user.getUserId()).child(userId).setValue(friend2);
    }

    public void deleteFriend(User user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference.child(userId).child(user.getUserId()).setValue(null);
        databaseReference.child(user.getUserId()).child(userId).setValue(null);
    }

    public void updateFriend(User user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference.child(userId).child(user.getUserId()).child("type").setValue("friend");
        databaseReference.child(user.getUserId()).child(userId).child("type").setValue("friend");
    }
    public void searchFriendType(final User user, final typeFriend type) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("friend").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendArrayList.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Friends account = keyNode.getValue(Friends.class);
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

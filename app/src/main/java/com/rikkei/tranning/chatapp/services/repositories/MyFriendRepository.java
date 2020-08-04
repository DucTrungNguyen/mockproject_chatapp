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

public class MyFriendRepository {
    DatabaseReference databaseReference;
    FriendsModel friends = new FriendsModel();
    UserModel user = new UserModel();
    ArrayList<UserModel> userArrayList = new ArrayList<>();
    ArrayList<FriendsModel> friendsArrayList = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoading(ArrayList<UserModel> arrayList);
    }

    public void getAllFriend(final DataStatus dataStatus) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("friend").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userArrayList.clear();
                for (DataSnapshot Node : dataSnapshot.getChildren()) {
                    FriendsModel friends = Node.getValue(FriendsModel.class);
                    if (friends.getType().equals("friend")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(friends.getFriendId());
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                user = dataSnapshot.getValue(UserModel.class);
                                userArrayList.add(user);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                dataStatus.DataIsLoading(userArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void searchFriend(String s, final DataStatus dataStatus) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = firebaseUser.getUid();
        Query query = FirebaseDatabase.getInstance().getReference("user").orderByChild("userName")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userArrayList.clear();
                for (DataSnapshot node : dataSnapshot.getChildren()) {
                    final UserModel users = node.getValue(UserModel.class);
                    databaseReference = FirebaseDatabase.getInstance().getReference("friend").child(userId).child(users.getUserId());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            FriendsModel friends = dataSnapshot.getValue(FriendsModel.class);
                            if (friends != null && friends.getType().equals("friend")) {
                                userArrayList.add(users);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                dataStatus.DataIsLoading(userArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
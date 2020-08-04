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
import com.rikkei.tranning.chatapp.services.models.AllUserModel;
import com.rikkei.tranning.chatapp.services.models.FriendsModel;
import com.rikkei.tranning.chatapp.services.models.UserModel;

import java.util.ArrayList;

public class AllFriendRepository {
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userId = firebaseUser.getUid();
    private ArrayList<UserModel> userList = new ArrayList<>();
    private ArrayList<AllUserModel> allUserArray = new ArrayList<>();
    String friendType;
    private ArrayList<FriendsModel> friendArrayList = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(ArrayList<UserModel> userArrayList);
    }

    public interface Data {
        void typeFriendIsLoad(ArrayList<AllUserModel> allUserModels);
    }

    public void getAllUser(final Data status) {
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allUserArray.clear();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    final UserModel account = keyNode.getValue(UserModel.class);
                    if (!account.getUserId().equals(userId)) {
                        final AllUserModel allUserModel = new AllUserModel();
                        allUserModel.setUserName(account.getUserName());
                        allUserModel.setUserImage(account.getUserImgUrl());
                        allUserModel.setUserId(account.getUserId());
                        databaseReference = FirebaseDatabase.getInstance().getReference("friend").child(userId);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                friendArrayList.clear();
                                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                                    FriendsModel friend = keyNode.getValue(FriendsModel.class);
                                    if (friend.getFriendId().equals(account.getUserId())) {
                                        friendArrayList.add(friend);
                                        if (friend.getType().equals("friend")) {
                                            friendType = "friend";
                                        } else if (friend.getType().equals("sendRequest")) {
                                            friendType = "sendRequest";
                                        } else if (friend.getType().equals("friendRequest")) {
                                            friendType = "friendRequest";
                                        }
                                    }
                                }
                                if (friendArrayList.size() == 0) {
                                    friendType = "NoFriend";
                                }
                                allUserModel.setUserType(friendType);
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    allUserArray.add(allUserModel);
                    }
                }
                status.typeFriendIsLoad(allUserArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Query searchUser(String s, final DataStatus dataStatus) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("user").orderByChild("userName")
                .startAt(s)
                .endAt(s + "\uf8ff");
        return query;
//        .addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                userList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    UserModel user = snapshot.getValue(UserModel.class);
//                    if (!user.getUserId().equals(firebaseUser.getUid())) {
//                        userList.add(user);
//                    }
//                }
//                dataStatus.DataIsLoaded(userList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    public void createFriend(AllUserModel user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        FriendsModel friend = new FriendsModel(user.getUserId(), "sendRequest");
        FriendsModel friend2 = new FriendsModel(userId, "friendRequest");
        databaseReference.child(userId).child(user.getUserId()).setValue(friend);
        databaseReference.child(user.getUserId()).child(userId).setValue(friend2);
    }

    public void deleteFriend(AllUserModel user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference.child(userId).child(user.getUserId()).setValue(null);
        databaseReference.child(user.getUserId()).child(userId).setValue(null);
    }

    public void updateFriend(AllUserModel user) {
        databaseReference = FirebaseDatabase.getInstance().getReference("friend");
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();
        databaseReference.child(userId).child(user.getUserId()).child("type").setValue("friend");
        databaseReference.child(user.getUserId()).child(userId).child("type").setValue("friend");
    }
}

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

public class RequestFriendRepository {
    String friendType;
    private ArrayList<FriendsModel> friendArrayList = new ArrayList<>();
    DatabaseReference databaseReference;
    FriendsModel friends=new FriendsModel();
    UserModel user=new UserModel();
    ArrayList<UserModel> userArrayList=new ArrayList<>();
    ArrayList<UserModel> requestUserArrayList=new ArrayList<>();
    ArrayList<UserModel> sendUserArrayList=new ArrayList<>();
    ArrayList<FriendsModel> friendsArrayList=new ArrayList<>();
    public interface DataStatus{
        void DataIsLoading(ArrayList<UserModel> arrayList);
    }

    public void getRequestFriend(final DataStatus dataStatus){
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userId=firebaseUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("friend").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestUserArrayList.clear();
//                sendUserArrayList.clear();
                for(DataSnapshot Node: dataSnapshot.getChildren()) {
                    friends = Node.getValue(FriendsModel.class);
                    assert friends != null;
                    if (friends.getType().equals("friendRequest")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(friends.getFriendId());

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                user=dataSnapshot.getValue(UserModel.class);
                                requestUserArrayList.add(user);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    dataStatus.DataIsLoading(requestUserArrayList);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getSendFriend(final DataStatus dataStatus){
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userId=firebaseUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("friend").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                requestUserArrayList.clear();
                sendUserArrayList.clear();
                for(DataSnapshot Node: dataSnapshot.getChildren()) {
                    friends = Node.getValue(FriendsModel.class);
                    assert friends != null;
                    if (friends.getType().equals("sendRequest")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(friends.getFriendId());

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                user=dataSnapshot.getValue(UserModel.class);
                                sendUserArrayList.add(user);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    dataStatus.DataIsLoading(sendUserArrayList);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void searchFriend(String s, final MyFriendRepository.DataStatus dataStatus) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("friend").child(firebaseUser.getUid()).orderByChild("friendId")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FriendsModel friends = snapshot.getValue(FriendsModel.class);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(friends.getFriendId());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<UserModel> userArrayList=new ArrayList<>();
                            user=dataSnapshot.getValue(UserModel.class);
                            userArrayList.add(user);
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


    public void searchFriendType(final UserModel user, final AllFriendRepository.typeFriend type) {
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
}

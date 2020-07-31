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
import com.rikkei.tranning.chatapp.services.models.Friends;
import com.rikkei.tranning.chatapp.services.models.User;

import java.util.ArrayList;

public class MyFriendRepositories {
    DatabaseReference databaseReference;
    Friends friends=new Friends();
    User user=new User();
    ArrayList<User> userArrayList=new ArrayList<>();
    ArrayList<Friends> friendsArrayList=new ArrayList<>();
    public interface DataStatus{
        void DataIsLoading(ArrayList<User> arrayList);
    }
    public void getAllFriend(final DataStatus dataStatus){
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String userId=firebaseUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("friend").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userArrayList.clear();
                for(DataSnapshot Node: dataSnapshot.getChildren()) {
                    friends = Node.getValue(Friends.class);
                    if (friends.getType().equals("friend")) {
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(friends.getFriendId());
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 user=dataSnapshot.getValue(User.class);
                                 userArrayList.add(user);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    dataStatus.DataIsLoading(userArrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void searchFriend(String s, final DataStatus dataStatus) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("friend").child(firebaseUser.getUid()).orderByChild("friendId")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Friends friends = snapshot.getValue(Friends.class);
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(friends.getFriendId());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<User> userArrayList=new ArrayList<>();
                            user=dataSnapshot.getValue(User.class);
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
}

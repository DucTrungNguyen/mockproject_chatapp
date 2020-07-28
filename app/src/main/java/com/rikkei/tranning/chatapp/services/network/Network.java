package com.rikkei.tranning.chatapp.services.network;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rikkei.tranning.chatapp.services.models.User;
public class Network {
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    private User account =new User();
    public interface DataStatus{
        void DataIsLoaded(User user);
    }
    public void infoUserFromFirebase(final DataStatus dataStatus){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        final String userId=firebaseUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("user").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                account=dataSnapshot.getValue(User.class);
                dataStatus.DataIsLoaded(account);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void updateInforFromFirebase(String key, String value){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String userId=firebaseUser.getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(userId).child(key).setValue(value);
    }
    public void createUserFromFirebase(User user,String userId){
        databaseReference= FirebaseDatabase.getInstance().getReference("user");
        databaseReference.child(userId).setValue(user);
    }
}

package com.rikkei.tranning.chatapp.viewmodels;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rikkei.tranning.chatapp.Base.BaseViewModel;
import com.rikkei.tranning.chatapp.Model.User;
import com.rikkei.tranning.chatapp.Navigator.SignUpNavigator;

import java.util.regex.Pattern;

public class SignUpViewModel extends BaseViewModel<SignUpNavigator> {
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    public void replaceFragmentClick(){
        getNavigator().replaceFragment();
    }
    public boolean validateEmailPassword(String email, String password){
        if(Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches() && Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
        ).matcher(password).matches()){
            return true;
        }
        else {
            return false;
        }
    }
    public void checkboxClick(){
        getNavigator().setEnableButton();
    }
    public TextWatcher nameTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getNavigator().setEnableButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(TextUtils.isEmpty(editable)){
                    getNavigator().requireName("Error");
                }
            }
        };
    }
    public TextWatcher emailTextWatcher(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getNavigator().setEnableButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
    public TextWatcher passTextWatcher(){
        return  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getNavigator().setEnableButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }
    public void signUpClick(){
        getNavigator().signup();
    }
    public void createUserFireBase(final String email, String password, final String name){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        databaseReference= FirebaseDatabase.getInstance().getReference("user");
                        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                        String userId=firebaseUser.getUid();
                        User user=new User(userId,name,email,"default","default","default");
                        databaseReference.child(userId).setValue(user);
                        getNavigator().showMessageSignUp("Sign Up Success!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getNavigator().showMessageSignUp("Sign Up Failed!");
                    }
                });
    }
}

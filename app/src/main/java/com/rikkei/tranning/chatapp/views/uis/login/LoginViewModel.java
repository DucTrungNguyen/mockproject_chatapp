package com.rikkei.tranning.chatapp.views.uis.login;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rikkei.tranning.chatapp.Base.BaseViewModel;

import java.util.regex.Pattern;

public class  LoginViewModel extends BaseViewModel<LoginNavigator> {
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    public void replaceFragmentClick(){
        getNavigator().replaceFragment();
    }
    public boolean validateEmailPassword(String email, String password) {
        if (Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches() && Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
        ).matcher(password).matches()) {
            return true;
        } else {
            return false;
        }
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

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public void loginFirebase(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        getNavigator().showMessageLogin("Login Success!");
                        getNavigator().moveIntent();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        getNavigator().showMessageLogin("Login Failed!");
                    }
                });
    }
    public void loginClick(){
        getNavigator().login();
    }
}

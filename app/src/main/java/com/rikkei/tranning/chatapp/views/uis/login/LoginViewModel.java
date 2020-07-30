package com.rikkei.tranning.chatapp.views.uis.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rikkei.tranning.chatapp.Base.BaseViewModel;
import com.rikkei.tranning.chatapp.services.models.User;
import com.rikkei.tranning.chatapp.services.models.loginUser;
import com.rikkei.tranning.chatapp.services.network.Network;

import java.util.ArrayList;
import java.util.regex.Pattern;
public class LoginViewModel extends ViewModel {
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();
    public MutableLiveData<Boolean> isOk=new MutableLiveData<>();
    private MutableLiveData<loginUser> userMutableLiveData;

    public MutableLiveData<loginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }
    public void onClick() {
        loginUser user = new loginUser(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(user);

    }
    public void loginFirebase(){
        firebaseAuth.signInWithEmailAndPassword(EmailAddress.getValue(),Password.getValue())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        isOk.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isOk.setValue(false);
                    }
                });
    }
}

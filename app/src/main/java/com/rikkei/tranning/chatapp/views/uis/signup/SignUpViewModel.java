package com.rikkei.tranning.chatapp.views.uis.signup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rikkei.tranning.chatapp.services.models.UserModel;
import com.rikkei.tranning.chatapp.services.models.LoginUserModel;
import com.rikkei.tranning.chatapp.services.repositories.SignUpRepositories;

public class SignUpViewModel extends ViewModel {
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    public MutableLiveData<String> userEmail = new MutableLiveData<>();
    public MutableLiveData<String> userPass = new MutableLiveData<>();
    public MutableLiveData<String> userName=new MutableLiveData<>();
    public MutableLiveData<Boolean> signUpSuccess=new MutableLiveData<>();
    public MutableLiveData<LoginUserModel> signUpUserMutableLiveData=new MutableLiveData<>();
    public void signUpButtonOnClick(){
        LoginUserModel user=new LoginUserModel(userEmail.getValue(),userPass.getValue());
        signUpUserMutableLiveData.setValue(user);
    }
//    public void replaceFragmentClick(){
//        getNavigator().replaceFragment();
//    }
//    public boolean validateEmailPassword(String email, String password){
//        if(Pattern.compile(
//                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
//                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
//                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
//                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
//                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
//        ).matcher(email).matches() && Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
//        ).matcher(password).matches()){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//    public void checkboxClick(){
//        getNavigator().setEnableButton();
//    }
//    public TextWatcher nameTextWatcher(){
//        return new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                getNavigator().setEnableButton();
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(TextUtils.isEmpty(editable)){
//                    getNavigator().requireName("Error");
//                }
//            }
//        };
//    }
//    public TextWatcher emailTextWatcher(){
//        return new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                getNavigator().setEnableButton();
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        };
//    }
//    public TextWatcher passTextWatcher(){
//        return  new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                getNavigator().setEnableButton();
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        };
//    }
//    public void signUpClick(){
//        getNavigator().signup();
//    }
    public void createUserFireBase(){
        firebaseAuth.createUserWithEmailAndPassword(userEmail.getValue(),userPass.getValue())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                        String userId=firebaseUser.getUid();
                        UserModel user=new UserModel(userId,userName.getValue(),userEmail.getValue(),"default","default","default");
                        new SignUpRepositories().createUserFromFirebase(user,userId);
                        //getNavigator().showMessageSignUp("Sign Up Success!");
                        signUpSuccess.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        signUpSuccess.setValue(false);
                    }
                });
    }
}

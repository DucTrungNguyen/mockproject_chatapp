package com.rikkei.tranning.chatapp.views.uis.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.tranning.chatapp.services.models.LoginUserModel
import com.rikkei.tranning.chatapp.services.models.UserModel
import com.rikkei.tranning.chatapp.services.repositories.SignUpRepository

class SignUpViewModel : ViewModel() {
    var firebaseAuth = FirebaseAuth.getInstance()
    var userEmail = MutableLiveData<String>()
    var userPass = MutableLiveData<String>()
    var userName = MutableLiveData<String>()
    var signUpSuccess = MutableLiveData<Boolean>()
    var signUpUserMutableLiveData = MutableLiveData<LoginUserModel>()

    var signUpStatus = MutableLiveData<SignUpStatus>()

    fun signUpButtonOnClick() {
        val user = LoginUserModel(userEmail.value!!, userPass.value!!)
//        signUpStatus.value = SignUpStatus.Loading(true)
//        val user = LoginUserModel(userEmail.value.toString(), userPass.value.toString())
        if (user.isEmailValid)
            signUpUserMutableLiveData.value = user

        if (!user.validateEmailPassword()) {
            signUpStatus.value = SignUpStatus.errorPassAndEmail(true)
        } else {
            createUserFireBase()
        }

    }

    fun createUserFireBase() {

        signUpStatus.value = SignUpStatus.Loading(true)
        firebaseAuth.createUserWithEmailAndPassword(
            userEmail.value!!,
            userPass.value!!
        )
            .addOnSuccessListener {
                val firebaseUser = firebaseAuth.currentUser
                val userId = firebaseUser!!.uid
                val user = UserModel(
                    userId,
                    userName.value,
                    userEmail.value,
                    "default",
                    "default",
                    "default"
                )
                SignUpRepository()
                    .createUserFromFirebase(user, userId)
                //getNavigator().showMessageSignUp("Sign Up Success!");
                signUpSuccess.value = true
                signUpStatus.value = SignUpStatus.isOk(true)
                signUpStatus.value = SignUpStatus.Loading(false)


            }
            .addOnFailureListener {
                signUpStatus.value = SignUpStatus.Failure(it)
                signUpStatus.value = SignUpStatus.isOk(false)
                signUpStatus.value = SignUpStatus.Loading(false)

            }
    }

    sealed class SignUpStatus {

        data class Loading(var loading: Boolean) : SignUpStatus()

        data class isOk(var isLogin: Boolean) : SignUpStatus()

        data class Failure(var e: Throwable) : SignUpStatus()

        data class ErrorPassAndEmail(var isError: Boolean) : SignUpStatus()

        companion object {

            fun loading(isLoading: Boolean): SignUpStatus = Loading(isLoading)

            fun success(isLogin: Boolean): SignUpStatus = isOk(isLogin)

            fun failure(e: Throwable): SignUpStatus = Failure(e)

            fun errorPassAndEmail(isErrorPassAndEmail: Boolean) =
                ErrorPassAndEmail(isErrorPassAndEmail)
        }

    }
}
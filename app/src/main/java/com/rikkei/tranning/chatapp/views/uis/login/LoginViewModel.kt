package com.rikkei.tranning.chatapp.views.uis.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.tranning.chatapp.services.models.LoginUserModel

class LoginViewModel : ViewModel() {
    var firebaseAuth = FirebaseAuth.getInstance()
    var EmailAddress = MutableLiveData<String>()
    var Password = MutableLiveData<String>()
    var isOk = MutableLiveData<Boolean>()
    private var userMutableLiveData: MutableLiveData<LoginUserModel>? = null
    val user: MutableLiveData<LoginUserModel>
        get() {
            if (userMutableLiveData == null) {
                userMutableLiveData = MutableLiveData()
            }
            return userMutableLiveData!!
        }

    fun onClick() {
        val user = LoginUserModel(EmailAddress.value, Password.value)
        userMutableLiveData!!.value = user
    }

    fun loginFirebase() {
        firebaseAuth.signInWithEmailAndPassword(
            EmailAddress.value!!,
            Password.value!!
        )
            .addOnSuccessListener { isOk.value = true }
            .addOnFailureListener { isOk.value = false }
    }


}
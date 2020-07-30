package com.rikkei.tranning.chatapp.views.uis.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.tranning.chatapp.services.models.LoginUserModel
import com.rikkei.tranning.chatapp.services.repositories.AuthRepository

class LoginViewModel : ViewModel() {



    var firebaseAuth = AuthRepository().getInstance()
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

        firebaseAuth?.loginUser( EmailAddress.value!!,
            Password.value!!)
            ?.addOnSuccessListener { isOk.value = true }
            ?.addOnFailureListener { isOk.value = false }
    }

    sealed class LoginStatus(){
        data class Loading(val isLoading :Boolean) : LoginStatus()
        data class isOk(val isOk : Boolean) : LoginStatus()
//        data class isFailure(e)
    }


}
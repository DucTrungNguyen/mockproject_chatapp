package com.rikkei.tranning.chatapp.views.uis.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rikkei.tranning.chatapp.services.models.LoginUserModel
import com.rikkei.tranning.chatapp.services.repositories.AuthRepository

class LoginViewModel : ViewModel() {

    var loginStatus = MutableLiveData<LoginStatus>()
    var firebaseAuth = AuthRepository
    var EmailAddress = MutableLiveData<String>()
    var Password = MutableLiveData<String>()

    //    var isOk = MutableLiveData<Boolean>()
    private var userMutableLiveData: MutableLiveData<LoginUserModel>? = null
    val user: MutableLiveData<LoginUserModel>
        get() {
            if (userMutableLiveData == null) {
                userMutableLiveData = MutableLiveData()
            }
            return userMutableLiveData!!
        }

    fun onClick() {
//        loginStatus.value = LoginStatus.loading(true)
//        loginFirebase()
        val user = LoginUserModel(EmailAddress.value.toString(), Password.value.toString())
        if (!user.validateEmailPassword()) {
            loginStatus.value = LoginStatus.ErrorPassAndEmail(true)
        } else {
            loginFirebase()
        }
//        if ( user.isEmailValid)
//        userMutableLiveData!!.value = user
    }

    private fun loginFirebase() {
        loginStatus.value = LoginStatus.Loading(true)
        firebaseAuth.loginUser(
            EmailAddress.value!!,
            Password.value!!
        )
            .addOnSuccessListener {
                loginStatus.value = LoginStatus.IsOk(true)
                loginStatus.value = LoginStatus.Loading(false)
//                isOk.value = true
            }
            .addOnFailureListener {
//                isOk.value = false
                loginStatus.value = LoginStatus.Failure(it)
            }
    }

    sealed class LoginStatus {

        data class Loading(var loading: Boolean) : LoginStatus()

        data class IsOk(var isLogin: Boolean) : LoginStatus()

        data class Failure(var e: Throwable) : LoginStatus()

        data class ErrorPassAndEmail(var isError: Boolean) : LoginStatus()

    }


}
package com.rikkei.tranning.chatapp.views.uis.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.tranning.chatapp.services.models.LoginUserModel
import com.rikkei.tranning.chatapp.services.repositories.AuthRepository

class LoginViewModel : ViewModel() {

    var loginStatus  = MutableLiveData<LoginStatus>()
    var firebaseAuth = AuthRepository().getInstance()
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
        loginFirebase()
//        val user = LoginUserModel(EmailAddress.value.toString(), Password.value.toString())
//        if (user.validateEmailPassword() ){
//            loginStatus.value = LoginStatus.errorPassAndEmail(true)
//        }else {
//            loginFirebase()
//        }
//        if ( user.isEmailValid)
//        userMutableLiveData!!.value = user
    }

    private fun loginFirebase() {
        loginStatus.value = LoginStatus.loading(true)
        firebaseAuth?.loginUser( EmailAddress.value!!,
            Password.value!!)
            ?.addOnSuccessListener {
                loginStatus.value = LoginStatus.isOk(true)
                loginStatus.value = LoginStatus.loading(false)
//                isOk.value = true
            }
            ?.addOnFailureListener {
//                isOk.value = false
                loginStatus.value = LoginStatus.failure(it)
            }
    }

    sealed class LoginStatus{

        data class Loading(var  loading: Boolean) : LoginStatus()

        data class isOk(var isLogin: Boolean) : LoginStatus()

        data class Failure(var e: Throwable) : LoginStatus()

        data class ErrorPassAndEmail ( var isError:  Boolean) : LoginStatus()

        companion object {

            fun loading(isLoading: Boolean): LoginStatus = Loading(isLoading)

            fun success(isLogin: Boolean): LoginStatus = isOk(isLogin)

            fun failure(e: Throwable): LoginStatus = Failure(e)

            fun errorPassAndEmail(isErrorPassAndEmail: Boolean) = ErrorPassAndEmail( isErrorPassAndEmail)
        }

    }


}
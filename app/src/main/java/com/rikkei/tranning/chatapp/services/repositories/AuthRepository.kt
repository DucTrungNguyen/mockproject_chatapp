package com.rikkei.tranning.chatapp.services.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class AuthRepository {
    var AUTH: AuthRepository? = null


    var firebaseAuth = FirebaseAuth.getInstance()
    var databaseReference: DatabaseReference? = null

    fun getInstance(): AuthRepository? {
        if (AUTH == null) {
            AUTH = AuthRepository()
        }
        return AUTH
    }

    fun loginUser(email : String, password : String) :Task<AuthResult>{
        return  firebaseAuth.signInWithEmailAndPassword(email, password)

    }
}
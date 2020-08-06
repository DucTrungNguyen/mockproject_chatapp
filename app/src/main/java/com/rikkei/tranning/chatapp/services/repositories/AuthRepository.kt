package com.rikkei.tranning.chatapp.services.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

object AuthRepository {

    private var firebaseAuth = FirebaseAuth.getInstance()
    private var databaseReference: DatabaseReference? = null

    fun loginUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)

    }
}
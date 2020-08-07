package com.rikkei.tranning.chatapp.views.uis

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.views.uis.login.LoginFragment

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.FrameLayout, LoginFragment(), null).commit()
        }
    }
}

package com.rikkei.tranning.chatapp.views.uis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.views.uis.login.LoginFragment

class SplashActivity : AppCompatActivity() {
    var txtappname: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        txtappname = findViewById(R.id.textViewAppName)
        val htmlcontent = "<b>Awesome </b>chat"
        txtappname!!.setText(Html.fromHtml(htmlcontent))
        val handler = Handler()
        handler.postDelayed({
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser != null) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
            }
            else {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val login = LoginFragment()
                fragmentTransaction.add(R.id.FrameLayout, login, null)
                fragmentTransaction.commit()

                startActivity(intent)
            }
        }, 3000)
    }
}
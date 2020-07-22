package com.rikkei.tranning.chatapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rikkei.tranning.chatapp.helper.PrefsHelper
import com.rikkei.tranning.chatapp.views.uis.MainActivity


class SplashActivity : AppCompatActivity() {
//    private var isLogined : Boolean = false
    private var isLoggedIn : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PrefsHelper.init(applicationContext)
//        val sharedPreferences:  SharedPreferences  = getSharedPreferences("CHAT_APP" , Context.MODE_PRIVATE)
        Handler().postDelayed(Runnable {
            isLoggedIn = PrefsHelper.readBoolean("LOGIN", false)!!

            if ( !isLoggedIn) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                finish()

            }
            else
            {

            }


        }, 3000)


    }
}
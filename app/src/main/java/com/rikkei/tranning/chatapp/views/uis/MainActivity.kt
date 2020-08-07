package com.rikkei.tranning.chatapp.views.uis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rikkei.tranning.chatapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayoutChat, MainFragment(), null).commit()
    }
}
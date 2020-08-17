package com.rikkei.tranning.chatapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MyAppOFFLINE extends Application {
    private static MyAppOFFLINE sInstance;

    public static MyAppOFFLINE self() {
        return sInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

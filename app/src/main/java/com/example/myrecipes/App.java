package com.example.myrecipes;

import android.app.Application;

import com.example.myrecipes.Utilities.SignalManager;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignalManager.init(this);
    }
}

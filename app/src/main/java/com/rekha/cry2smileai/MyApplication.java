package com.rekha.cry2smileai;

import android.app.Application;

import androidx.work.Configuration;
import androidx.work.WorkManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (!WorkManager.isInitialized()) {
            Configuration configuration = new Configuration.Builder().build();
            WorkManager.initialize(this, configuration);
        }
    }
}

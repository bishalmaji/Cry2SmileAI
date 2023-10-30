package com.rekha.cry2smileai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyWorkerScheduler {

    private static final String PREFS_NAME = "MyWorkerPrefs";
    private static final String WORK_SCHEDULED_KEY = "workScheduled";

    public static void scheduleWork(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (!prefs.getBoolean(WORK_SCHEDULED_KEY, false)) {
            // Work has not been scheduled yet, so schedule it.

            // Define the constraints for your work.
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED) // Set your desired network constraint
                    .build();

            // Calculate the delay to run the work before 5 am.
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 5);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            long currentTimeMillis = System.currentTimeMillis();
            if (calendar.getTimeInMillis() <= currentTimeMillis) {
                // If the specified time is in the past (i.e., after 5 am today), schedule it for tomorrow.
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            long initialDelay = calendar.getTimeInMillis() - currentTimeMillis;

            // Create a OneTimeWorkRequest to schedule the work.
            WorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                    .setConstraints(constraints)
                    .build();

            // Enqueue the work request to schedule it.
            WorkManager.getInstance().enqueue(myWorkRequest);

            // Set the flag to indicate that the work has been scheduled.
            prefs.edit().putBoolean(WORK_SCHEDULED_KEY, true).apply();
        }
    }
}


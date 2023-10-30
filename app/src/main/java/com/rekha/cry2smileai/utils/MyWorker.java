package com.rekha.cry2smileai.utils;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.rekha.cry2smileai.MyDatabase;
import com.rekha.cry2smileai.daos.FridayDao;
import com.rekha.cry2smileai.daos.MondayDao;
import com.rekha.cry2smileai.daos.SaturdayDao;
import com.rekha.cry2smileai.daos.SundayDao;
import com.rekha.cry2smileai.daos.ThursdayDao;
import com.rekha.cry2smileai.daos.TuesdayDao;
import com.rekha.cry2smileai.daos.WednesdayDao;

import java.util.Calendar;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.util.Calendar;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Get the current day of the week.
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Define table names for each day of the week. Adjust these names based on your actual table names.
        String[] tableNames = {
                "sunday_table",
                "monday_table",
                "tuesday_table",
                "wednesday_table",
                "thursday_table",
                "friday_table",
                "saturday_table"
        };

        // Get the table name for the current day.
        String tableName = tableNames[dayOfWeek - 1]; // Calendar.DAY_OF_WEEK starts from 1 for Sunday.

        // Delete all data from the table for the current day.
        MyDatabase database = MyDatabase.getInstance(getApplicationContext());
        switch (tableName) {
            case "sunday_table":
                database.getSundayDao().deleteAllData();
                break;
            case "monday_table":
                database.getMondayDao().deleteAllData();
                break;
            case "tuesday_table":
                database.getTuesdayDao().deleteAllData();
                break;
            case "wednesday_table":
                database.getWednesdayDao().deleteAllData();
                break;
            case "thursday_table":
                database.getThursdayDao().deleteAllData();
                break;
            case "friday_table":
                database.getFridayDao().deleteAllData();
                break;
            case "saturday_table":
                database.getSaturdayDao().deleteAllData();
                break;
        }

        return Result.success();
    }
}

